package com.leticiafernandes.letsmovie.infrastructure.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leticiafernandes.letsmovie.infrastructure.model.Genre
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.infrastructure.persistence.dao.GenreDao
import com.leticiafernandes.letsmovie.infrastructure.persistence.dao.MovieDao

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Database(entities = arrayOf(Movie::class, Genre::class), version = 1)
abstract class LetsMovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    companion object {
        private var INSTANCE: LetsMovieDataBase? = null

        fun getInstance(context: Context): LetsMovieDataBase? {
            if (INSTANCE == null) {
                synchronized(LetsMovieDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            LetsMovieDataBase::class.java, "letsmovie.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}