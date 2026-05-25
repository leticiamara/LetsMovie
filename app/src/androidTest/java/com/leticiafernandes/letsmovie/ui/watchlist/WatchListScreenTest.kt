package com.leticiafernandes.letsmovie.ui.watchlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.fake.FakeAndroidFavoritesRepository
import com.leticiafernandes.letsmovie.fake.buildAndroidFakeMovie
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WatchListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createViewModel(repository: FakeAndroidFavoritesRepository) =
        WatchlistViewModel(
            observeFavoritesUseCase = ObserveFavoritesUseCase(repository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(repository, IsFavoriteUseCase(repository))
        )

    @Test
    fun showsEmptyMessageWhenNoFavorites() {
        val viewModel = createViewModel(FakeAndroidFavoritesRepository())

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("You haven't favorited any movies yet.")
            .assertIsDisplayed()
    }

    @Test
    fun showsMovieTitleWhenContentState() {
        val repository = FakeAndroidFavoritesRepository()
        val movieTitle = "Interstellar"
        repository.seed(buildAndroidFakeMovie(id = 1L, title = movieTitle))
        val viewModel = createViewModel(repository)

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(movieTitle).assertIsDisplayed()
    }

    @Test
    fun showsAllMovieTitlesWhenMultipleFavorites() {
        val repository = FakeAndroidFavoritesRepository()
        val movieTitle1 = "Inception"
        val movieTitle2 = "The Dark Knight"
        val movieTitle3 = "Interstellar"
        repository.seed(
            buildAndroidFakeMovie(id = 1L, title = movieTitle1),
            buildAndroidFakeMovie(id = 2L, title = movieTitle2),
            buildAndroidFakeMovie(id = 3L, title = movieTitle3)
        )
        val viewModel = createViewModel(repository)

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(movieTitle1).assertIsDisplayed()
        composeTestRule.onNodeWithText(movieTitle2).assertIsDisplayed()
        composeTestRule.onNodeWithText(movieTitle3).assertIsDisplayed()
    }

    @Test
    fun showsErrorMessageWhenFlowThrows() {
        val errorMessage = "Failed to load favorites"
        val repository = FakeAndroidFavoritesRepository(
            throwOnObserve = RuntimeException(errorMessage)
        )
        val viewModel = createViewModel(repository)

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun removingFavoriteUpdatesTheList() {
        val repository = FakeAndroidFavoritesRepository()
        val movieTitle1 = "Inception"
        val movieTitle2 = "The Dark Knight"
        repository.seed(
            buildAndroidFakeMovie(id = 1L, title = movieTitle1),
            buildAndroidFakeMovie(id = 2L, title = movieTitle2)
        )
        val viewModel = createViewModel(repository)

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(movieTitle1).assertIsDisplayed()

        composeTestRule.onAllNodesWithContentDescription("Remove from favorites")[0]
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(movieTitle1).assertDoesNotExist()
        composeTestRule.onNodeWithText(movieTitle2).assertIsDisplayed()
    }

    @Test
    fun clickingMovieCardTriggersOnMovieClick() {
        val repository = FakeAndroidFavoritesRepository()
        val movieTitle = "Inception"
        val movie = buildAndroidFakeMovie(id = 1L, title = movieTitle)
        repository.seed(movie)
        val viewModel = createViewModel(repository)
        var clickedMovie: FavoriteMovieItem? = null

        composeTestRule.setContent {
            WatchListScreen(viewModel = viewModel, onMovieClick = { clickedMovie = it })
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(movieTitle).performClick()

        assert(clickedMovie?.id == 1L)
    }
}
