package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.MovieResult
import io.reactivex.rxjava3.core.Single

interface MoviesRepository {
    fun listPopularMovies(page: Int): Single<MovieResult>
}
