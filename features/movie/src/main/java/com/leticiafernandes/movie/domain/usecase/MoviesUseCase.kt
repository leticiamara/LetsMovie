package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.MovieResultItem

private const val FIRST_PAGE = 1

interface MoviesUseCase {
    suspend fun listPopularMovies(page: Int = FIRST_PAGE): MovieResultItem
    suspend fun listMovieDetails(movieId: Long): MovieItem
}
