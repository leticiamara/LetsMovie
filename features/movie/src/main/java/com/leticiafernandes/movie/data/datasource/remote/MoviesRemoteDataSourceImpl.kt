package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.api.MoviesService
import com.leticiafernandes.movie.data.datasource.remote.dto.MovieDTO
import com.leticiafernandes.movie.data.datasource.remote.dto.MovieResultDTO
import javax.inject.Inject

class MoviesRemoteDataSourceImpl @Inject constructor(
        private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun listPopularMovies(page: Int): MovieResultDTO {
        return moviesService.listPopularMovies(page = page)
    }

    override suspend fun listMovieDetails(movieId: Long): MovieDTO {
        return moviesService.listMovieDetails(movieId)
    }
}
