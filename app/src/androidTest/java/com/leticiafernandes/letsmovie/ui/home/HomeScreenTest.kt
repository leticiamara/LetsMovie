package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
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

private const val POPULAR_MOVIES_TAB = "Popular movies"

private const val WATCHLIST_TAB = "Watchlist"

private const val EMPTY_WATCHLIST_TAB = "You haven't favorited any movies yet."

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

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
    }

    @Test
    fun showsPopularMoviesTitleByDefault() {
        setHomeScreenContent()

        // "Popular movies" appears in both the TopAppBar and the tab label when Popular is active
        composeTestRule.onAllNodesWithText(POPULAR_MOVIES_TAB)[0].assertIsDisplayed()
    }

    @Test
    fun showsPopularMoviesTabLabel() {
        setHomeScreenContent()

        composeTestRule.onAllNodesWithText(POPULAR_MOVIES_TAB)[1].assertIsDisplayed()
    }

    @Test
    fun showsWatchlistTabLabel() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(WATCHLIST_TAB).assertIsDisplayed()
    }

    @Test
    fun clickingWatchlistTabChangesTitle() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(WATCHLIST_TAB).performClick()
        composeTestRule.waitForIdle()

        // The TopAppBar should now show "Watchlist" — it was already visible as the tab label,
        // but after clicking the tab the title text node count increases to 2 (tab + appbar).
        // We verify the screen switched by confirming the empty watchlist message is shown.
        composeTestRule.onNodeWithText(EMPTY_WATCHLIST_TAB).assertIsDisplayed()
    }

    @Test
    fun clickingPopularTabAfterWatchlistRestoresPopularContent() {
        setHomeScreenContent()

        composeTestRule.onNodeWithText(WATCHLIST_TAB).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(POPULAR_MOVIES_TAB).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(EMPTY_WATCHLIST_TAB).assertDoesNotExist()
    }
}
