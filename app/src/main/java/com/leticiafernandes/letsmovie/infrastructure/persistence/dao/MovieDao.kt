package com.leticiafernandes.letsmovie.infrastructure.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
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