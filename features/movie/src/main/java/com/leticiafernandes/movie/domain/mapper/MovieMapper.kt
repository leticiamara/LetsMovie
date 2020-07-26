package com.leticiafernandes.movie.domain.mapper

import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.MovieResultItem

fun MovieResult.mapToMovieResultItem(): MovieResultItem {
    this.apply {
        return MovieResultItem(page, totalResults, totalPages, mapToMovieItemList(results))
    }
}

private fun mapToMovieItemList(movieList: List<Movie>): List<MovieItem> {
    return movieList.map { mapToMovieItem(it) }
}

private fun mapToMovieItem(movie: Movie): MovieItem {
    movie.apply {
        return MovieItem(
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
                listOf() //TODO Get gender list
        )
    }
}
