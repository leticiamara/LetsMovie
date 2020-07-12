package com.leticiafernandes.movies.data.datasource.remote

import com.leticiafernandes.movies.data.api.MoviesService
import com.leticiafernandes.movies.data.datasource.remote.dto.GenreDTO
import io.reactivex.rxjava3.core.Observable

class GenresRemoteDataSourceImpl(private val moviesService: MoviesService) : GenresRemoteDataSource {

    override fun listAllGenres(): Observable<List<GenreDTO>> {
        return moviesService.listAllGenres().map {
            it.genres
        }
    }
}
