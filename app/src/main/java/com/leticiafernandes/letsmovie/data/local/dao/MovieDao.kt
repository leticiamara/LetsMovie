package com.leticiafernandes.letsmovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leticiafernandes.letsmovie.data.model.Movie

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Dao
interface MovieDao {

    @Query("SELECT * from movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * from movie where id = :id")
    fun findMovieById(id: Long): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(movie: Movie)
}