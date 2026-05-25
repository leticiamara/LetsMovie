package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeMoviesRemoteDataSource
import com.leticiafernandes.letsmovie.fake.buildFakeMovieDTO
import com.leticiafernandes.letsmovie.util.buildHttpException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
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
    fun `listMovieDetails returns Success with mapped domain movie`() = runTest {
        val movieId = 42L
        remoteDataSource.movieDetailsResult = buildFakeMovieDTO(id = movieId, title = "Inception", voteAverage = 8.8)

        val result = repository.listMovieDetails(movieId)

        assertTrue(result is NetworkResult.Success)
        val movie = (result as NetworkResult.Success).data
        assertEquals(movieId, movie.id)
        assertEquals("Inception", movie.title)
        assertEquals(8.8, movie.voteAverage, 0.0)
    }

    @Test
    fun `listMovieDetails maps genreIds from dto`() = runTest {
        remoteDataSource.movieDetailsResult = buildFakeMovieDTO(id = DEFAULT_MOVIE_ID)
            .copy(genreIds = listOf(28L, 12L))

        val result = repository.listMovieDetails(DEFAULT_MOVIE_ID)

        assertEquals(listOf(28L, 12L), (result as NetworkResult.Success).data.genreIds)
    }

    @Test
    fun `listMovieDetails returns NetworkError when IOException is thrown`() = runTest {
        remoteDataSource.exception = IOException("Timeout")

        val result = repository.listMovieDetails(DEFAULT_MOVIE_ID)

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `listMovieDetails returns HttpError when HttpException is thrown`() = runTest {
        remoteDataSource.exception = buildHttpException(404)

        val result = repository.listMovieDetails(DEFAULT_MOVIE_ID)

        assertTrue(result is NetworkResult.HttpError)
        assertEquals(404, (result as NetworkResult.HttpError).code)
    }
}
