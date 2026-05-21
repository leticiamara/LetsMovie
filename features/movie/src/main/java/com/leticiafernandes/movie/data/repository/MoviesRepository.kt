package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult
import com.leticiafernandes.movie.presentation.model.MovieItem
import io.reactivex.rxjava3.core.Single

interface MoviesRepository {
    fun listPopularMovies(page: Int): Single<MovieResult>

    fun listMovieDetails(movieId: Long): Single<Movie>
}
