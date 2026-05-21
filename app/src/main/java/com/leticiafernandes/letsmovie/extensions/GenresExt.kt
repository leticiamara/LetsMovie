package com.leticiafernandes.letsmovie.extensions

import com.leticiafernandes.letsmovie.ui.movie.model.GenreItem

private const val MOVIE_GENRE_SEPARATOR = " | "

fun List<GenreItem>.toStringGenres(): String {
    return this.joinToString(
            separator = MOVIE_GENRE_SEPARATOR,
            transform = { it.name }
    )
}