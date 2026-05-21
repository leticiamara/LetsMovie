package com.leticiafernandes.letsmovie.domain.mapper

import com.leticiafernandes.letsmovie.domain.model.Genre
import com.leticiafernandes.letsmovie.ui.movie.model.GenreItem

fun Genre.mapToGenreItem(): GenreItem {
    this.apply {
        return GenreItem(
                id,
                name
        )
    }
}
