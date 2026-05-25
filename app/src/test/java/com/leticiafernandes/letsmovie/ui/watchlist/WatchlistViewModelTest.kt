package com.leticiafernandes.letsmovie.ui.watchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.fake.FakeFavoritesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovie
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
class WatchlistViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeFavoritesRepository
    private lateinit var viewModel: WatchlistViewModel

    @Before
    fun setUp() {
        repository = FakeFavoritesRepository()
        viewModel = WatchlistViewModel(
            observeFavoritesUseCase = ObserveFavoritesUseCase(repository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(repository, IsFavoriteUseCase(repository))
        )
    }

    @Test
    fun `when favorites list is empty, uiState is Empty`() = runTest {
        advanceUntilIdle()

        assertEquals(WatchlistUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun `when favorites list has items, uiState is Content with mapped items`() = runTest {
        val movie1 = buildFakeMovie(1L)
        val movie2 = buildFakeMovie(2L)
        repository.fillFavorites(movie1, movie2)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Content)
        state as WatchlistUiState.Content
        assertEquals(2, state.favorites.size)
        assertEquals(movie1.id, state.favorites[0].id)
        assertEquals(movie1.title, state.favorites[0].title)
        assertEquals(movie2.id, state.favorites[1].id)
    }

    @Test
    fun `when favorites flow throws, uiState is Error`() = runTest {
        val databaseErrorMessage = "Database error"
        repository.observeError = RuntimeException(databaseErrorMessage)
        val errorViewModel = WatchlistViewModel(
            observeFavoritesUseCase = ObserveFavoritesUseCase(repository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(repository, IsFavoriteUseCase(repository))
        )
        advanceUntilIdle()

        val state = errorViewModel.uiState.value
        assertTrue(state is WatchlistUiState.Error)
        assertTrue((state as WatchlistUiState.Error).message.contains(databaseErrorMessage))
    }

    @Test
    fun `toggleFavorite removes movie when it is already in favorites`() = runTest {
        repository.fillFavorites(buildFakeMovie(1L), buildFakeMovie(2L))
        advanceUntilIdle()

        viewModel.toggleFavorite(1L)
        advanceUntilIdle()

        val state = viewModel.uiState.value as WatchlistUiState.Content
        assertEquals(1, state.favorites.size)
        assertEquals(2L, state.favorites[0].id)
    }

    @Test
    fun `toggleFavorite sets uiState to Error when toggle fails`() = runTest {
        repository.addFavoriteResult = NetworkResult.HttpError(503, "Service Unavailable")
        advanceUntilIdle()

        viewModel.toggleFavorite(99L)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is WatchlistUiState.Error)
    }

    @Test
    fun `uiState transitions to Content when favorite is added via toggleFavorite`() = runTest {
        advanceUntilIdle()
        assertEquals(WatchlistUiState.Empty, viewModel.uiState.value)

        viewModel.toggleFavorite(1L)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Content)
        assertEquals(1L, (state as WatchlistUiState.Content).favorites[0].id)
    }
}
