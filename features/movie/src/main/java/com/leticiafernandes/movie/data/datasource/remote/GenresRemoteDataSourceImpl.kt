package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.api.MoviesService
import com.leticiafernandes.movie.data.datasource.remote.dto.GenreDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GenresRemoteDataSourceImpl @Inject constructor(
        private val moviesService: MoviesService
) : GenresRemoteDataSource {

    override fun listAllGenres(): Single<List<GenreDTO>> {
        return moviesService.listAllGenres().map {
            it.genres
        }
    }
}
