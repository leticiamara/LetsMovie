package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.api.MoviesService
import com.leticiafernandes.movie.data.datasource.remote.dto.MovieResultDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
        private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override fun listPopularMovies(page: Int): Single<MovieResultDTO> {
        return moviesService.listPopularMovies(page = page)
    }
}
