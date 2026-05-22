package com.leticiafernandes.letsmovie.data.repository

import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie>
}
