package com.leticiafernandes.movie.data.repository

import com.leticiafernandes.movie.data.datasource.remote.MoviesRemoteDataSource
import com.leticiafernandes.movie.data.mapper.mapToMovie
import com.leticiafernandes.movie.data.mapper.mapToMovieResultDomain
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.model.MovieResult
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override suspend fun listPopularMovies(page: Int): MovieResult {
        return moviesRemoteDataSource.listPopularMovies(page).mapToMovieResultDomain()
    }

    override suspend fun listMovieDetails(movieId: Long): Movie {
        return mapToMovie(moviesRemoteDataSource.listMovieDetails(movieId))
    }
}
