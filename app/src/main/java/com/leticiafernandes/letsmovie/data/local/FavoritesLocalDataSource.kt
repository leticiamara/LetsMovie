package com.leticiafernandes.letsmovie.data.local

import com.leticiafernandes.letsmovie.data.model.FavoriteMovieData
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {
    fun observeAll(): Flow<List<FavoriteMovieData>>
    suspend fun findById(id: Long): FavoriteMovieData?
    fun observeIsFavorite(id: Long): Flow<Boolean>
    suspend fun upsert(entity: FavoriteMovieData)
    suspend fun deleteById(id: Long)
}