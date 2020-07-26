package com.leticiafernandes.movie.data.mapper

import com.leticiafernandes.movie.data.datasource.remote.dto.GenreDTO
import com.leticiafernandes.movie.domain.model.Genre

fun GenreDTO.mapToGenreDomain(): Genre {
    this.apply {
        return Genre(
                id,
                name
        )
    }
}
