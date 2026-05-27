package com.leticiafernandes.letsmovie.ui.movie

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.fake.FakeAndroidFavoritesRepository
import com.leticiafernandes.letsmovie.fake.FakeAndroidMoviesRepository
import com.leticiafernandes.letsmovie.fake.buildAndroidFakeMovieDomain
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailScreenTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createViewModel(
        movieId: Long? = 1L,
        repository: FakeAndroidMoviesRepository = FakeAndroidMoviesRepository()
    ): MovieDetailViewModel {
        val favoritesRepository = FakeAndroidFavoritesRepository()
        return MovieDetailViewModel(
            savedStateHandle = if (movieId != null) {
                SavedStateHandle(mapOf("movieId" to movieId))
            } else {
                SavedStateHandle()
            },
            moviesUseCase = MoviesUseCase(repository),
            toggleFavoriteUseCase = ToggleFavoriteUseCase(favoritesRepository, IsFavoriteUseCase(favoritesRepository)),
            isFavoriteUseCase = IsFavoriteUseCase(favoritesRepository)
        )
    }

    @Test
    fun showsMovieDetailTitle() {
        val viewModel = createViewModel()

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }

        composeTestRule.onNodeWithText(context.getString(R.string.movie_detail_title)).assertIsDisplayed()
    }

    @Test
    fun showsMovieTitleWhenSuccess() {
        val repository = FakeAndroidMoviesRepository()
        repository.movieDetailsResult = NetworkResult.Success(buildAndroidFakeMovieDomain(title = "Inception"))
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
    }

    @Test
    fun showsRatingWhenSuccess() {
        val repository = FakeAndroidMoviesRepository()
        repository.movieDetailsResult = NetworkResult.Success(buildAndroidFakeMovieDomain(voteAverage = 8.5))
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("8.5").assertIsDisplayed()
    }

    @Test
    fun showsOverviewLabelAndTextWhenSuccess() {
        val repository = FakeAndroidMoviesRepository()
        val overviewText = "A thief who steals corporate secrets."
        repository.movieDetailsResult = NetworkResult.Success(buildAndroidFakeMovieDomain(overview = overviewText))
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(context.getString(R.string.overview)).assertIsDisplayed()
        composeTestRule.onNodeWithText(overviewText).assertIsDisplayed()
    }

    @Test
    fun showsServerErrorMessageOnHttpError() {
        val repository = FakeAndroidMoviesRepository()
        repository.movieDetailsResult = NetworkResult.HttpError(404, "Not Found")
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(context.getString(R.string.error_server, 404)).assertIsDisplayed()
    }

    @Test
    fun showsNetworkErrorMessageOnNetworkError() {
        val repository = FakeAndroidMoviesRepository()
        repository.movieDetailsResult = NetworkResult.NetworkError(RuntimeException("Timeout"))
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(context.getString(R.string.error_network)).assertIsDisplayed()
    }

    @Test
    fun clickingBackButtonTriggersOnBackClick() {
        var backClicked = false
        val repository = FakeAndroidMoviesRepository()
        repository.movieDetailsResult = NetworkResult.Success(buildAndroidFakeMovieDomain())
        val viewModel = createViewModel(repository = repository)

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = { backClicked = true })
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.navigate_back)).performClick()

        assertTrue(backClicked)
    }
}
