package com.leticiafernandes.movie.domain.mapper

import com.leticiafernandes.movie.domain.model.Genre
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.MovieResultItem

fun MovieResult.mapToMovieResultItem(moviesGenres: List<Genre>): MovieResultItem {
    this.apply {
        return MovieResultItem(
                page,
                totalResults,
                totalPages,
                mapToMovieItemList(results, moviesGenres)
        )
    }
}

private fun mapToMovieItemList(movieList: List<Movie>, moviesGenres: List<Genre>): List<MovieItem> {
    return movieList.map {
        val movieGenreList = moviesGenres.filter { genre -> genre.id in it.genreIds }
        mapToMovieItem(it, movieGenreList)
    }
}

private fun mapToMovieItem(movie: Movie, movieGenreList: List<Genre>): MovieItem {
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
                movieGenreList.map { it.mapToGenreItem() }
        )
    }
}
