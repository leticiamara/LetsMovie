package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.data.datasource.remote.MoviesRemoteDataSource
import com.leticiafernandes.movie.data.mapper.mapToMovie
import com.leticiafernandes.movie.data.mapper.mapToMovieResultDomain
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult
import com.leticiafernandes.movie.presentation.model.MovieItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override fun listPopularMovies(page: Int): Single<MovieResult> {
        return moviesRemoteDataSource.listPopularMovies(page).map {
            it.mapToMovieResultDomain()
        }
    }

    override fun listMovieDetails(movieId: Long): Single<Movie> {
        return moviesRemoteDataSource.listMovieDetails(movieId).map {
            mapToMovie(it)
        }
    }
}
