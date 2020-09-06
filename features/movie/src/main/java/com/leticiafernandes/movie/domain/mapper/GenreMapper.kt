package com.leticiafernandes.movie.domain.mapper

import com.leticiafernandes.movie.domain.model.Genre
import com.leticiafernandes.movie.presentation.model.GenreItem

fun Genre.mapToGenreItem(): GenreItem {
    this.apply {
        return GenreItem(
                id,
                name
        )
    }
}
