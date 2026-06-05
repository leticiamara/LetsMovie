package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.domain.model.Genre

interface GenresRepository {
    suspend fun listAllGenres(): NetworkResult<List<Genre>>
}
