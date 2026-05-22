package com.leticiafernandes.letsmovie.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.MoviesRepository
import com.leticiafernandes.letsmovie.domain.mapper.mapToMovieItem
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : MoviesUseCase {

    override fun getPopularMovies(): Flow<PagingData<MovieItem>> =
        moviesRepository.getPopularMovies().map { pagingData ->
            pagingData.map { movie -> mapToMovieItem(movie, null) }
        }

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<MovieItem> =
        moviesRepository.listMovieDetails(movieId).let { result ->
            when (result) {
                is NetworkResult.Success -> NetworkResult.Success(mapToMovieItem(result.data, null))
                is NetworkResult.HttpError -> result
                is NetworkResult.NetworkError -> result
            }
        }
}
