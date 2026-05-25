package com.leticiafernandes.letsmovie.ui.movie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.fake.FakeAndroidMoviesRepository
import com.leticiafernandes.letsmovie.fake.buildAndroidFakeMovieDomain
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val NAVIGATE_BACK = "Navigate back"

@RunWith(AndroidJUnit4::class)
class MovieDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createViewModel(
        movieId: Long? = 1L,
        repository: FakeAndroidMoviesRepository = FakeAndroidMoviesRepository()
    ) = MovieDetailViewModel(
        savedStateHandle = if (movieId != null) {
            SavedStateHandle(mapOf("movieId" to movieId))
        } else {
            SavedStateHandle()
        },
        moviesUseCase = MoviesUseCase(repository)
    )

    @Test
    fun showsMovieDetailTitle() {
        val viewModel = createViewModel()

        composeTestRule.setContent {
            MovieDetailScreen(viewModel = viewModel, onBackClick = {})
        }

        composeTestRule.onNodeWithText("Movie Detail").assertIsDisplayed()
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

        composeTestRule.onNodeWithText("Rating: 8.5").assertIsDisplayed()
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

        composeTestRule.onNodeWithText("Overview").assertIsDisplayed()
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

        composeTestRule.onNodeWithText("Server error 404: Not Found").assertIsDisplayed()
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

        composeTestRule.onNodeWithText("Network error. Please check your connection.").assertIsDisplayed()
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

        composeTestRule.onNodeWithContentDescription(NAVIGATE_BACK).performClick()

        assertTrue(backClicked)
    }
}
