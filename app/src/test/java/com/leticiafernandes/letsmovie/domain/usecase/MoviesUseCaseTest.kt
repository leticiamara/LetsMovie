package com.leticiafernandes.letsmovie.domain.usecase

import androidx.paging.testing.asSnapshot
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeMoviesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovieDomain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesUseCaseTest {

    private lateinit var repository: FakeMoviesRepository
    private lateinit var useCase: MoviesUseCase

    @Before
    fun setUp() {
        repository = FakeMoviesRepository()
        useCase = MoviesUseCase(repository)
    }

    @Test
    fun `getPopularMovies maps Movie to MovieItem`() = runTest {
        val movie = buildFakeMovieDomain(id = 42L)
        repository.movies = listOf(movie)

        val snapshot = useCase.getPopularMovies().asSnapshot()

        assertEquals(1, snapshot.size)
        assertEquals(movie.id, snapshot[0].id)
        assertEquals(movie.title, snapshot[0].title)
        assertEquals(movie.voteAverage, snapshot[0].voteAverage, 0.0)
    }

    @Test
    fun `getPopularMovies with empty list emits empty snapshot`() = runTest {
        repository.movies = emptyList()

        val snapshot = useCase.getPopularMovies().asSnapshot()

        assertTrue(snapshot.isEmpty())
    }

    @Test
    fun `listMovieDetails returns Success with MovieItem when repository succeeds`() = runTest {
        val movie = buildFakeMovieDomain(id = 1L)
        repository.movieDetailsResult = NetworkResult.Success(movie)

        val result = useCase.listMovieDetails(1L)

        assertTrue(result is NetworkResult.Success)
        result as NetworkResult.Success
        assertEquals(movie.id, result.data.id)
        assertEquals(movie.title, result.data.title)
    }

    @Test
    fun `listMovieDetails returns HttpError unchanged`() = runTest {
        repository.movieDetailsResult = NetworkResult.HttpError(404, "Not Found")

        val result = useCase.listMovieDetails(1L)

        assertEquals(NetworkResult.HttpError(404, "Not Found"), result)
    }

    @Test
    fun `listMovieDetails returns NetworkError unchanged`() = runTest {
        val exception = RuntimeException("timeout")
        repository.movieDetailsResult = NetworkResult.NetworkError(exception)

        val result = useCase.listMovieDetails(1L)

        assertTrue(result is NetworkResult.NetworkError)
        result as NetworkResult.NetworkError
        assertEquals(exception, result.throwable)
    }
}
