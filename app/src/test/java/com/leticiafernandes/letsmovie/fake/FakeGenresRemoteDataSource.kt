package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.GenresRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO

class FakeGenresRemoteDataSource : GenresRemoteDataSource {

    var result: List<GenreDTO> = emptyList()
    var exception: Exception? = null

    override suspend fun listAllGenres(): List<GenreDTO> {
        exception?.let { throw it }
        return result
    }
}
