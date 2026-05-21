package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult

interface MoviesRepository {
    suspend fun listPopularMovies(page: Int): MovieResult
    suspend fun listMovieDetails(movieId: Long): Movie
}
