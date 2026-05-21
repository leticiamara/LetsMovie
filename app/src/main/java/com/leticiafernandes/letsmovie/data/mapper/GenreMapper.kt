package com.leticiafernandes.letsmovie.data.mapper

import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO
import com.leticiafernandes.letsmovie.domain.model.Genre

fun GenreDTO.mapToGenreDomain(): Genre {
    this.apply {
        return Genre(
                id,
                name
        )
    }
}
