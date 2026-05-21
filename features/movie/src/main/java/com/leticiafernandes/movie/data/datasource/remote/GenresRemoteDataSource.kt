package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.datasource.remote.dto.GenreDTO

interface GenresRemoteDataSource {
    suspend fun listAllGenres(): List<GenreDTO>
}
