package com.leticiafernandes.letsmovie.data.local

import com.leticiafernandes.letsmovie.data.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {
    fun observeAll(): Flow<List<FavoriteMovieEntity>>
    fun observeIsFavorite(movieId: Long): Flow<Boolean>
    suspend fun upsert(entity: FavoriteMovieEntity)
    suspend fun deleteById(movieId: Long)
}