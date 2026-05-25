package com.leticiafernandes.letsmovie.data.repository

import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieCategory
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(category: MovieCategory): Flow<PagingData<Movie>>
    suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie>
}
