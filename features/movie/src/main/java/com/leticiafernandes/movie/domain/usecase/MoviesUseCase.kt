package com.leticiafernandes.movie.domain.usecase

import com.leticiafernandes.movie.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable

private const val FIRST_PAGE = 1

interface MoviesUseCase {
    fun listPopularMovies(page: Int = FIRST_PAGE): Observable<MovieResult>
}