package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.local.dao.FavoritesLocalDataSource
import com.leticiafernandes.letsmovie.data.remote.FavoritesRemoteDataSource
import com.leticiafernandes.letsmovie.data.mapper.toDomain
import com.leticiafernandes.letsmovie.data.mapper.toEntity
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.safeApiCall
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val localDataSource: FavoritesLocalDataSource,
    private val remoteDataSource: FavoritesRemoteDataSource
) : FavoritesRepository {

    override fun observeFavorites(): Flow<List<FavoriteMovie>> =
        localDataSource.observeAll().map { entities -> entities.map { it.toDomain() } }

    override fun observeIsFavorite(movieId: Long): Flow<Boolean> =
        localDataSource.observeIsFavorite(movieId)

    override suspend fun addFavorite(movieId: Long): NetworkResult<Unit> =
        safeApiCall {
            val dto = remoteDataSource.fetchMovieDetails(movieId)
            localDataSource.upsert(dto.toEntity())
        }

    override suspend fun removeFavorite(movieId: Long) {
        localDataSource.deleteById(movieId)
    }
}
