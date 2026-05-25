package com.leticiafernandes.letsmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leticiafernandes.letsmovie.data.mapper.mapToMovie
import com.leticiafernandes.letsmovie.data.remote.MoviesRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.MoviesPagingSource
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.safeApiCall
import com.leticiafernandes.letsmovie.domain.model.Movie
import com.leticiafernandes.letsmovie.domain.model.MovieCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override fun getMovies(category: MovieCategory): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(remoteDataSource, category) }
    ).flow

    override suspend fun listMovieDetails(movieId: Long): NetworkResult<Movie> =
        safeApiCall { mapToMovie(remoteDataSource.listMovieDetails(movieId)) }
}
