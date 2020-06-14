package com.leticiafernandes.letsmovie.infrastructure.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.leticiafernandes.letsmovie.infrastructure.model.Movie

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Dao
interface MovieDao {

    @Query("SELECT * from movie")
    fun getAll(): List<Movie>

    @Query("SELECT * from movie where id = :id")
    fun findMovieById(id: Long): Movie

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Update(onConflict = REPLACE)
    fun update(movie: Movie)
}