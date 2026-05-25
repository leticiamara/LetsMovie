package com.leticiafernandes.letsmovie.ui.movie

import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.fake.FakeFavoritesRepository
import com.leticiafernandes.letsmovie.fake.FakeMoviesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovie
import com.leticiafernandes.letsmovie.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var favoritesRepository: FakeFavoritesRepository
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        favoritesRepository = FakeFavoritesRepository()
        val isFavoriteUseCase = IsFavoriteUseCase(favoritesRepository)
        viewModel = MoviesViewModel(
            moviesUseCase = MoviesUseCase(FakeMoviesRepository()),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(favoritesRepository, isFavoriteUseCase),
            observeFavoritesUseCase = ObserveFavoritesUseCase(favoritesRepository)
        )
    }

    @Test
    fun `bookmarkedIds initial value is empty set`() = runTest {
        assertEquals(emptySet<Long>(), viewModel.bookmarkedIds.value)
    }

    @Test
    fun `bookmarkedIds updates when favorites are added`() = runTest {
        val collected = mutableListOf<Set<Long>>()
        val job = launch { viewModel.bookmarkedIds.collect { collected.add(it) } }

        favoritesRepository.fillFavorites(buildFakeMovie(1L), buildFakeMovie(2L))
        advanceUntilIdle()

        assertTrue(collected.any { it.containsAll(listOf(1L, 2L)) })
        job.cancel()
    }

    @Test
    fun `toggleBookmark adds movie to favorites when not bookmarked`() = runTest {
        viewModel.toggleBookmark(1L)
        advanceUntilIdle()

        assertTrue(favoritesRepository.observeIsFavorite(1L).first())
    }

    @Test
    fun `toggleBookmark removes movie from favorites when already bookmarked`() = runTest {
        favoritesRepository.fillFavorites(buildFakeMovie(1L))

        viewModel.toggleBookmark(1L)
        advanceUntilIdle()

        assertFalse(favoritesRepository.observeIsFavorite(1L).first())
    }
}
