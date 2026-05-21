package com.leticiafernandes.movie.data.datasource.remote

import com.leticiafernandes.movie.data.datasource.remote.dto.MovieDTO
import com.leticiafernandes.movie.data.datasource.remote.dto.MovieResultDTO

interface MoviesRemoteDataSource {
    suspend fun listPopularMovies(page: Int): MovieResultDTO
    suspend fun listMovieDetails(movieId: Long): MovieDTO
}
