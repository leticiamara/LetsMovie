package com.leticiafernandes.letsmovie.ui.watchlist

import com.leticiafernandes.letsmovie.fake.buildFakeMovie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Date

class FavoriteMovieMapperTest {

    @Test
    fun `toItem maps all fields from FavoriteMovie to FavoriteMovieItem`() {
        val date = Date(1_700_000_000_000L)
        val movie = buildFakeMovie(id = 5L).copy(
            voteAverage = 8.5,
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            overview = "A great movie",
            releaseDate = date
        )

        val item = movie.toItem()

        assertEquals(movie.id, item.id)
        assertEquals(movie.title, item.title)
        assertEquals(movie.voteAverage, item.voteAverage, 0.0)
        assertEquals(movie.posterPath, item.posterPath)
        assertEquals(movie.backdropPath, item.backdropPath)
        assertEquals(movie.overview, item.overview)
        assertEquals(date, item.releaseDate)
    }

    @Test
    fun `toItem maps null poster and backdrop paths as null`() {
        val movie = buildFakeMovie(id = 1L).copy(posterPath = null, backdropPath = null)

        val item = movie.toItem()

        assertNull(item.posterPath)
        assertNull(item.backdropPath)
    }
}
