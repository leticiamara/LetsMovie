package com.leticiafernandes.letsmovie.data.local.dao

import com.leticiafernandes.letsmovie.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {
    fun observeAll(): Flow<List<FavoriteMovieEntity>>
    suspend fun findById(id: Long): FavoriteMovieEntity?
    fun observeIsFavorite(id: Long): Flow<Boolean>
    suspend fun upsert(entity: FavoriteMovieEntity)
    suspend fun deleteById(id: Long)
}
