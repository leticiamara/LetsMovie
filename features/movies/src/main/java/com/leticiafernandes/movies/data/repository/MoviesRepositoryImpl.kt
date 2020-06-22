package com.leticiafernandes.movies.data.repository

import com.leticiafernandes.movies.data.datasource.remote.MoviesRemoteDataSource
import com.leticiafernandes.movies.data.mapper.MovieMapper
import com.leticiafernandes.movies.domain.model.MovieResult
import io.reactivex.rxjava3.core.Observable

class MoviesRepositoryImpl(
        private val moviesRemoteDataSource: MoviesRemoteDataSource,
        private val movieMapper: MovieMapper
) : MoviesRepository {

    override fun listPopularMovies(page: Int): Observable<MovieResult> {
        return moviesRemoteDataSource.listPopularMovies(page).map {
            movieMapper.mapTo(it)
        }
    }
}