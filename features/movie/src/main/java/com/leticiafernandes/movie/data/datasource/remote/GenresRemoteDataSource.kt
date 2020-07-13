package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.datasource.remote.dto.GenreDTO
import io.reactivex.rxjava3.core.Observable

interface GenresRemoteDataSource {
    fun listAllGenres(): Observable<List<GenreDTO>>
}
