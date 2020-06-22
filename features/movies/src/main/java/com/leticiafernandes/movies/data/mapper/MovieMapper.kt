package com.leticiafernandes.movies.data.mapper

import com.leticiafernandes.movies.data.datasource.remote.dto.MovieDTO
import com.leticiafernandes.movies.data.datasource.remote.dto.MovieResultDTO
import com.leticiafernandes.movies.domain.model.Movie
import com.leticiafernandes.movies.domain.model.MovieResult

class MovieMapper {

    fun mapTo(movieResultDTO: MovieResultDTO): MovieResult {
        movieResultDTO.apply {
            return MovieResult(page, totalResults, totalPages, mapTo(results))
        }
    }

    private fun mapTo(moviesDTOs: List<MovieDTO>): List<Movie> {
        return moviesDTOs.map { mapTo(it) }
    }

    private fun mapTo(movieDTO: MovieDTO): Movie {
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
}
