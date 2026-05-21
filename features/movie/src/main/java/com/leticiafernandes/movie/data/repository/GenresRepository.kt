package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.Genre

interface GenresRepository {
    suspend fun listAllGenres(): List<Genre>
}
