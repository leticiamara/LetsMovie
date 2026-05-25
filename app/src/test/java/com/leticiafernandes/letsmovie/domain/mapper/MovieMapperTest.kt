package com.leticiafernandes.letsmovie.domain.mapper

import com.leticiafernandes.letsmovie.domain.model.Genre
import com.leticiafernandes.letsmovie.fake.buildFakeMovieDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Date

class MovieMapperTest {

    private val genreList = listOf(Genre(id = 28L, name = "Action"), Genre(id = 12L, name = "Adventure"))

    @Test
    fun `all fields are correctly mapped from Movie to MovieItem`() {
        val movie = buildFakeMovieDomain(id = 7L)

        val item = mapToMovieItem(movie, null)

        assertEquals(movie.id, item.id)
        assertEquals(movie.voteCount, item.voteCount)
        assertEquals(movie.title, item.title)
        assertEquals(movie.video, item.video)
        assertEquals(movie.voteAverage, item.voteAverage, 0.0)
        assertEquals(movie.popularity, item.popularity, 0.0)
        assertEquals(movie.posterPath, item.posterPath)
        assertEquals(movie.originalLanguage, item.originalLanguage)
        assertEquals(movie.originalTitle, item.originalTitle)
        assertEquals(movie.genreIds, item.genreIds)
        assertEquals(movie.backdropPath, item.backdropPath)
        assertEquals(movie.adult, item.adult)
        assertEquals(movie.overview, item.overview)
        assertEquals(movie.releaseDate, item.releaseDate)
    }

    @Test
    fun `when movie has genres, uses movie genres and ignores passed genre list`() {
        val genreName = "Sci-Fi"
        val genreId = 99L
        val movieGenres = listOf(Genre(id = genreId, name = genreName))
        val movie = buildFakeMovieDomain().copy(genres = movieGenres)

        val item = mapToMovieItem(movie, genreList)

        assertEquals(1, item.genres?.size)
        assertEquals(genreId, item.genres?.first()?.id)
        assertEquals(genreName, item.genres?.first()?.name)
    }

    @Test
    fun `when movie has no genres but genre list is provided, uses genre list`() {
        val movie = buildFakeMovieDomain().copy(genres = null)

        val item = mapToMovieItem(movie, genreList)

        assertEquals(2, item.genres?.size)
        assertEquals(28L, item.genres?.get(0)?.id)
        assertEquals(12L, item.genres?.get(1)?.id)
    }

    @Test
    fun `when both movie genres and genre list are null, MovieItem genres is null`() {
        val movie = buildFakeMovieDomain().copy(genres = null)

        val item = mapToMovieItem(movie, null)

        assertNull(item.genres)
    }

    @Test
    fun `when movie genres is empty list, uses empty list instead of genre list`() {
        val movie = buildFakeMovieDomain().copy(genres = emptyList())

        val item = mapToMovieItem(movie, genreList)

        assertEquals(emptyList<Any>(), item.genres)
    }

    @Test
    fun `releaseDate is preserved exactly`() {
        val date = Date(1_700_000_000_000L)
        val movie = buildFakeMovieDomain().copy(releaseDate = date)

        val item = mapToMovieItem(movie, null)

        assertEquals(date, item.releaseDate)
    }
}
