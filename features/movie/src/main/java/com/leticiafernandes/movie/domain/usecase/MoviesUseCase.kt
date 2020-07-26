package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.presentation.model.MovieResultItem
import io.reactivex.rxjava3.core.Single

private const val FIRST_PAGE = 1

interface MoviesUseCase {
    fun listPopularMovies(page: Int = FIRST_PAGE): Single<MovieResultItem>
}
