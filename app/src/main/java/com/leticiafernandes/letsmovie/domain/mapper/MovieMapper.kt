package com.leticiafernandes.letsmovie.domain.mapper

import com.leticiafernandes.letsmovie.domain.model.Genre
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem

fun mapToMovieItem(movie: Movie, movieGenreList: List<Genre>?): MovieItem {
    movie.apply {
        val genresToMap = genres ?: movieGenreList
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
            genresToMap?.map { it.mapToGenreItem() }
        )
    }
}
