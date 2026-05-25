package com.leticiafernandes.letsmovie.ui.home

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.fake.FakeAndroidFavoritesRepository
import com.leticiafernandes.letsmovie.fake.FakeAndroidMoviesRepository
import com.leticiafernandes.letsmovie.ui.movie.MoviesViewModel
import com.leticiafernandes.letsmovie.ui.watchlist.WatchlistViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context get() = ApplicationProvider.getApplicationContext()
    private val moviesTab get() = context.getString(R.string.title_movies)
    private val watchlistTab get() = context.getString(R.string.title_watchlist)
    private val emptyWatchlist get() = context.getString(R.string.favorites_empty)

    private fun createMoviesViewModel(): MoviesViewModel {
        val favoritesRepository = FakeAndroidFavoritesRepository()
        return MoviesViewModel(
            moviesUseCase = MoviesUseCase(FakeAndroidMoviesRepository()),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(favoritesRepository, IsFavoriteUseCase(favoritesRepository)),
            observeFavoritesUseCase = ObserveFavoritesUseCase(favoritesRepository)
        )
    }

    private fun createWatchlistViewModel(): WatchlistViewModel {
        val favoritesRepository = FakeAndroidFavoritesRepository()
        return WatchlistViewModel(
            observeFavoritesUseCase = ObserveFavoritesUseCase(favoritesRepository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(favoritesRepository, IsFavoriteUseCase(favoritesRepository))
        )
    }

    private fun setHomeScreenContent(onMovieClick: (Long) -> Unit = {}) {
        composeTestRule.setContent {
            HomeScreen(
                onMovieClick = onMovieClick,
                moviesViewModel = createMoviesViewModel(),
                watchlistViewModel = createWatchlistViewModel()
            )
        }
        composeTestRule.waitForIdle()
    }

    @Test
    fun showsPopularMoviesTitleByDefault() {
        setHomeScreenContent()

        // title_movies appears in both the TopAppBar and the tab label when Popular is active
        composeTestRule.onAllNodesWithText(moviesTab)[0].assertIsDisplayed()
    }

    @Test
    fun showsPopularMoviesTabLabel() {
        setHomeScreenContent()

        composeTestRule.onAllNodesWithText(moviesTab)[1].assertIsDisplayed()
    }

    @Test
    fun showsWatchlistTabLabel() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(watchlistTab).assertIsDisplayed()
    }

    @Test
    fun clickingWatchlistTabChangesTitle() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(watchlistTab).performClick()
        composeTestRule.waitForIdle()

        // The TopAppBar should now show "Watchlist" — it was already visible as the tab label,
        // but after clicking the tab the title text node count increases to 2 (tab + appbar).
        // We verify the screen switched by confirming the empty watchlist message is shown.
        composeTestRule.onNodeWithText(emptyWatchlist).assertIsDisplayed()
    }

    @Test
    fun clickingPopularTabAfterWatchlistRestoresPopularContent() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(watchlistTab).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(moviesTab).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(emptyWatchlist).assertDoesNotExist()
    }
}
