package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.datasource.remote.dto.GenreDTO
import io.reactivex.rxjava3.core.Single

interface GenresRemoteDataSource {
    fun listAllGenres(): Single<List<GenreDTO>>
}
