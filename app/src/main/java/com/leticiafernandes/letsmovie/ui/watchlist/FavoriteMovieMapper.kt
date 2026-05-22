package com.leticiafernandes.letsmovie.ui.watchlist

import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie

fun FavoriteMovie.toItem(): FavoriteMovieItem = FavoriteMovieItem(
    id = id,
    title = title,
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = releaseDate
)
