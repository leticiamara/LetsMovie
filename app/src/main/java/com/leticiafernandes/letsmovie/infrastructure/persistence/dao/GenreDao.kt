package com.leticiafernandes.letsmovie.infrastructure.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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