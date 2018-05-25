package com.leticiafernandes.letsmovie.infrastructure.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.leticiafernandes.letsmovie.infrastructure.data.dao.MovieDao
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieEntity

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class LetsMovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

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