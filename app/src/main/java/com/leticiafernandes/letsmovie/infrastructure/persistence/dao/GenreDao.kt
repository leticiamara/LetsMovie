package com.leticiafernandes.letsmovie.infrastructure.persistence.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.leticiafernandes.letsmovie.infrastructure.model.Genre

/**
 * Created by leticiafernandes on 27/05/18.
 */
@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genres: List<Genre>)

    @Query("SELECT * from genre where id IN(:genreIds)")
    fun findByIds(genreIds: List<Long>) : List<Genre>
}