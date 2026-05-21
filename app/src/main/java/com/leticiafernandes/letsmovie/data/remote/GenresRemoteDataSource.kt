package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO

interface GenresRemoteDataSource {
    suspend fun listAllGenres(): List<GenreDTO>
}
