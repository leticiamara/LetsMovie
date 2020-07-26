package com.leticiafernandes.movie.data.mapper

import com.leticiafernandes.movie.data.datasource.remote.dto.MovieDTO
import com.leticiafernandes.movie.data.datasource.remote.dto.MovieResultDTO
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult

fun MovieResultDTO.mapToMovieResultDomain(): MovieResult {
    this.apply {
        return MovieResult(page, totalResults, totalPages, mapToMovies(results))
    }
}

private fun mapToMovies(moviesDTOs: List<MovieDTO>): List<Movie> {
    return moviesDTOs.map { mapToMovie(it) }
}

private fun mapToMovie(movieDTO: MovieDTO): Movie {
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
                genreIds,
                backdropPath,
                adult,
                overview,
                releaseDate,
                listOf() //TODO
        )
    }
}
