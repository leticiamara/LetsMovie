package com.leticiafernandes.letsmovie.ui.movie

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class MovieCardTest {

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeMovie = MovieItem(
        id = 1L,
        voteCount = 100,
        title = "Inception",
        video = null,
        voteAverage = 8.8,
        popularity = 200.0,
        posterPath = null,
        originalLanguage = "en",
        originalTitle = "Inception",
        genreIds = listOf(28L),
        backdropPath = null,
        adult = false,
        overview = "A thief who steals corporate secrets.",
        releaseDate = Date(1_279_324_800_000L),
        genres = null
    )

    @Test
    fun showsMovieTitle() {
        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = false,
                onBookmarkClick = {},
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText("Inception").assertIsDisplayed()
    }

    @Test
    fun showsReleaseDateFormatted() {
        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = false,
                onBookmarkClick = {},
                onClick = {}
            )
        }

        composeTestRule.onNodeWithText(fakeMovie.releaseDate.formatToReleaseDate()).assertIsDisplayed()
    }

    @Test
    fun showsAddToFavoritesWhenNotBookmarked() {
        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = false,
                onBookmarkClick = {},
                onClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_to_favorites)).assertIsDisplayed()
    }

    @Test
    fun showsRemoveFromFavoritesWhenBookmarked() {
        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = true,
                onBookmarkClick = {},
                onClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.remove_from_favorites)).assertIsDisplayed()
    }

    @Test
    fun clickingCardTriggersOnClick() {
        var clicked = false

        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = false,
                onBookmarkClick = {},
                onClick = { clicked = true }
            )
        }

        composeTestRule.onNodeWithText("Inception").performClick()

        assertTrue(clicked)
    }

    @Test
    fun clickingBookmarkButtonTriggersOnBookmarkClick() {
        var bookmarkClicked = false

        composeTestRule.setContent {
            MovieCard(
                movie = fakeMovie,
                isBookmarked = false,
                onBookmarkClick = { bookmarkClicked = true },
                onClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.add_to_favorites)).performClick()

        assertTrue(bookmarkClicked)
    }
}
