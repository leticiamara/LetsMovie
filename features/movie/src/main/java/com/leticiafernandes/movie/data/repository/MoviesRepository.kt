package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable

interface MoviesRepository {
    fun listPopularMovies(page: Int): Observable<MovieResult>
}