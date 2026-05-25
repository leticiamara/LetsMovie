package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeMoviesRemoteDataSource
import com.leticiafernandes.letsmovie.fake.buildFakeMovieDTO
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val DEFAULT_MOVIE_ID = 1L

class MoviesRepositoryImplTest {

    private lateinit var remoteDataSource: FakeMoviesRemoteDataSource
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = FakeMoviesRemoteDataSource()
        repository = MoviesRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `listMovieDetails returns Success with mapped domain movie`() {
        val movieTitle = "Inception"
        val movieId = 42L
        val voteAverage = 8.8
        runTest {
            remoteDataSource.movieDetailsResult = buildFakeMovieDTO(id = movieId, title = movieTitle, voteAverage = voteAverage)

            val result = repository.listMovieDetails(movieId)

            assertTrue(result is NetworkResult.Success)
            val movie = (result as NetworkResult.Success).data
            assertEquals(movieId, movie.id)
            assertEquals(movieTitle, movie.title)
            assertEquals(voteAverage, movie.voteAverage, 0.0)
        }
    }

    @Test
    fun `listMovieDetails maps genreIds from dto`() {
        val movieId = DEFAULT_MOVIE_ID
        val genreId1 = 28L
        val genreId2 = 12L
        runTest {
            remoteDataSource.movieDetailsResult = buildFakeMovieDTO(id = movieId).copy(genreIds = listOf(
                genreId1, genreId2
            ))

            val result = repository.listMovieDetails(movieId)

            val movie = (result as NetworkResult.Success).data
            assertEquals(listOf(genreId1, genreId2), movie.genreIds)
        }
    }

    @Test
    fun `listMovieDetails returns NetworkError when IOException is thrown`() = runTest {
        remoteDataSource.exception = IOException("Timeout")

        val result = repository.listMovieDetails(DEFAULT_MOVIE_ID)

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `listMovieDetails returns HttpError when HttpException is thrown`() {
        val errorCode = 404
        runTest {
            remoteDataSource.exception = HttpException(Response.error<Unit>(errorCode, "".toResponseBody(null)))

            val result = repository.listMovieDetails(DEFAULT_MOVIE_ID)

            assertTrue(result is NetworkResult.HttpError)
            assertEquals(errorCode, (result as NetworkResult.HttpError).code)
        }
    }

}
