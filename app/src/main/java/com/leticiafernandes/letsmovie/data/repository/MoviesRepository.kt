package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieResult

interface MoviesRepository {
    suspend fun listPopularMovies(page: Int): NetworkResult<MovieResult>
    suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie>
}
