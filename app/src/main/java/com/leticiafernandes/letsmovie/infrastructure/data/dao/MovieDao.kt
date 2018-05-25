package com.leticiafernandes.letsmovie.infrastructure.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieEntity

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Dao
interface MovieDao {

    @Query("SELECT * from movie")
    fun getAll(): List<MovieEntity>

    @Insert(onConflict = REPLACE)
    fun insert(movie: MovieEntity)
}