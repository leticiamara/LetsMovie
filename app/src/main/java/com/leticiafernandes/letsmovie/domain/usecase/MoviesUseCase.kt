package com.leticiafernandes.letsmovie.domain.usecase

import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getPopularMovies(): Flow<PagingData<MovieItem>>
    suspend fun listMovieDetails(movieId: Long): NetworkResult<MovieItem>
}
