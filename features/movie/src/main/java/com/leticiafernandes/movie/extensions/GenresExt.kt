package com.leticiafernandes.movie.extensions

import com.leticiafernandes.movie.presentation.model.GenreItem

private const val MOVIE_GENRE_SEPARATOR = " | "

fun List<GenreItem>.toStringGenres(): String {
    return this.joinToString(
            separator = MOVIE_GENRE_SEPARATOR,
            transform = { it.name }
    )
}