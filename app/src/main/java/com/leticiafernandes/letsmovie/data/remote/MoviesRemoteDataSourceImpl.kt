package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.api.MoviesService
import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO
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
