package com.leticiafernandes.letsmovie.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.fake.FakeMoviesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovieDomain
import com.leticiafernandes.letsmovie.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesRepository: FakeMoviesRepository

    @Before
    fun setUp() {
        moviesRepository = FakeMoviesRepository()
    }

    private fun createViewModel(movieId: Long?) = MovieDetailViewModel(
        savedStateHandle = SavedStateHandle(
            if (movieId != null) mapOf("movieId" to movieId) else emptyMap()
        ),
        moviesUseCase = MoviesUseCase(moviesRepository)
    )

    @Test
    fun `when fetch succeeds, uiState is ShowMovieInfo with correct movie`() = runTest {
        val movie = buildFakeMovieDomain(id = 1L)
        moviesRepository.movieDetailsResult = NetworkResult.Success(movie)

        val viewModel = createViewModel(movieId = 1L)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ShowMovieInfo)
        assertEquals(movie.id, (state as ShowMovieInfo).movie.id)
        assertEquals(movie.title, state.movie.title)
    }

    @Test
    fun `when fetch returns HttpError, uiState is ShowMovieError Http with error code`() = runTest {
        moviesRepository.movieDetailsResult = NetworkResult.HttpError(404, "Not Found")

        val viewModel = createViewModel(movieId = 1L)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is ShowMovieError.Http)
        assertEquals(404, (state as ShowMovieError.Http).code)
    }

    @Test
    fun `when fetch returns NetworkError, uiState is ShowMovieError Network`() = runTest {
        moviesRepository.movieDetailsResult = NetworkResult.NetworkError(RuntimeException("timeout"))

        val viewModel = createViewModel(movieId = 1L)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(ShowMovieError.Network, state)
    }

    @Test
    fun `when movieId is null, uiState is ShowMovieEmptyState`() = runTest {
        val viewModel = createViewModel(movieId = null)
        advanceUntilIdle()

        assertEquals(ShowMovieEmptyState, viewModel.uiState.value)
    }
}
