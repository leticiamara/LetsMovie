package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO

interface MoviesRemoteDataSource {
    suspend fun listPopularMovies(page: Int): MovieResultDTO
    suspend fun listMovieDetails(movieId: Long): MovieDTO
}
