package com.leticiafernandes.letsmovie.data.mapper

import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val releaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun MovieResultDTO.mapToMovieResultDomain(): MovieResult {
    this.apply {
        return MovieResult(page, totalResults, totalPages, mapToMovies(results))
    }
}

private fun mapToMovies(moviesDTOs: List<MovieDTO>): List<Movie> {
    return moviesDTOs.map { mapToMovie(it) }
}

fun mapToMovie(movieDTO: MovieDTO): Movie {
    movieDTO.apply {
        return Movie(
                id,
                voteCount,
                title,
                video,
                voteAverage,
                popularity,
                posterPath,
                originalLanguage,
                originalTitle,
                genreIds ?: emptyList(),
                backdropPath,
                adult,
                overview,
                parseReleaseDate(releaseDate),
                genres?.map { it.mapToGenreDomain() }
        )
    }
}

private fun parseReleaseDate(dateString: String): Date =
    if (dateString.isNotEmpty()) runCatching { releaseDateFormat.parse(dateString)!! }.getOrDefault(Date(0))
    else Date(0)
