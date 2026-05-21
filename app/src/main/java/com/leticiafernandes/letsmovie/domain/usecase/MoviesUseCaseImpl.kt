package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.getOrElse
import com.leticiafernandes.letsmovie.data.repository.GenresRepository
import com.leticiafernandes.letsmovie.data.repository.MoviesRepository
import com.leticiafernandes.letsmovie.domain.mapper.mapToMovieItem
import com.leticiafernandes.letsmovie.domain.mapper.mapToMovieResultItem
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import com.leticiafernandes.letsmovie.ui.movie.model.MovieResultItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
        private val moviesRepository: MoviesRepository,
        private val genresRepository: GenresRepository
) : MoviesUseCase {

    override suspend fun listPopularMovies(page: Int): NetworkResult<MovieResultItem> = coroutineScope {
        //val genresDeferred = async { genresRepository.listAllGenres() }
        val moviesDeferred = async { moviesRepository.listPopularMovies(page) }

        //val genres = genresDeferred.await().getOrElse { return@coroutineScope it }
        val movies = moviesDeferred.await().getOrElse { return@coroutineScope it }

        NetworkResult.Success(movies.mapToMovieResultItem(null))
    }

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<MovieItem> {
        return moviesRepository.listMovieDetails(movieId).let { result ->
            when (result) {
                is NetworkResult.Success -> NetworkResult.Success(mapToMovieItem(result.data, null))
                is NetworkResult.HttpError -> result
                is NetworkResult.NetworkError -> result
            }
        }
    }
}
