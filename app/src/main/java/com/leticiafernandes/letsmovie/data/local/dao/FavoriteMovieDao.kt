package com.leticiafernandes.letsmovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies ORDER BY favoriteAt DESC")
    fun observeAll(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE movieId = :movieId)")
    fun isFavorite(movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE movieId = :movieId")
    suspend fun deleteById(movieId: Long)
}