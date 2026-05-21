package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.MoviesRemoteDataSource
import com.leticiafernandes.letsmovie.data.mapper.mapToMovie
import com.leticiafernandes.letsmovie.data.mapper.mapToMovieResultDomain
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.safeApiCall
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieResult
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override suspend fun listPopularMovies(page: Int): NetworkResult<MovieResult> =
        safeApiCall { moviesRemoteDataSource.listPopularMovies(page).mapToMovieResultDomain() }

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie> =
        safeApiCall { mapToMovie(moviesRemoteDataSource.listMovieDetails(movieId)) }
}
