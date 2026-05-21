package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import com.leticiafernandes.letsmovie.ui.movie.model.MovieResultItem

private const val FIRST_PAGE = 1

interface MoviesUseCase {
    suspend fun listPopularMovies(page: Int = FIRST_PAGE): NetworkResult<MovieResultItem>
    suspend fun listMovieDetails(movieId: Long): NetworkResult<MovieItem>
}
