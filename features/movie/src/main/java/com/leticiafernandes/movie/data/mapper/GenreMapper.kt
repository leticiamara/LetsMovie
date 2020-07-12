package com.leticiafernandes.movies.data.mapper

import com.leticiafernandes.movies.data.datasource.remote.dto.GenreDTO
import com.leticiafernandes.movies.domain.model.Genre

class GenreMapper {

    fun mapTo(genresDTOs: List<GenreDTO>): List<Genre> {
        return genresDTOs.map { mapTo(it) }
    }

    private fun mapTo(genreDTO: GenreDTO): Genre {
        genreDTO.apply {
            return Genre(
                    id,
                    name
            )
        }
    }
}
