package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun observeFavorites(): Flow<List<FavoriteMovie>>

    fun observeIsFavorite(movieId: Long): Flow<Boolean>

    suspend fun addFavorite(movieId: Long): NetworkResult<Unit>

    suspend fun removeFavorite(movieId: Long)
}
