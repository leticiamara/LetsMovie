package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.data.datasource.remote.MoviesRemoteDataSource
import com.leticiafernandes.movie.data.mapper.mapToMovieResultDomain
import com.leticiafernandes.movie.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override fun listPopularMovies(page: Int): Observable<MovieResult> {
        return moviesRemoteDataSource.listPopularMovies(page).map {
            it.mapToMovieResultDomain()
        }
    }
}
