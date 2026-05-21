package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.api.MoviesService
import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO
import javax.inject.Inject

class GenresRemoteDataSourceImpl @Inject constructor(
        private val moviesService: MoviesService
) : GenresRemoteDataSource {

    override suspend fun listAllGenres(): List<GenreDTO> {
        return moviesService.listAllGenres().genres
    }
}
