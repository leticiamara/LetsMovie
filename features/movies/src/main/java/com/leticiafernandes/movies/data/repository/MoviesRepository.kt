package com.leticiafernandes.movies.data.repository

import com.leticiafernandes.movies.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable

interface MoviesRepository {
    fun listPopularMovies(page: Int): Observable<MovieResult>
}