package com.leticiafernandes.letsmovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leticiafernandes.letsmovie.data.local.converter.DateConverter
import com.leticiafernandes.letsmovie.data.local.dao.FavoriteMovieDao
import com.leticiafernandes.letsmovie.data.local.dao.GenreDao
import com.leticiafernandes.letsmovie.data.local.dao.MovieDao
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieData
import com.leticiafernandes.letsmovie.data.model.Genre
import com.leticiafernandes.letsmovie.data.model.Movie

@Database(
    entities = [Movie::class, Genre::class, FavoriteMovieData::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class LetsMovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        private var INSTANCE: LetsMovieDataBase? = null

        val DATABASE_NAME = "letsmovie.db"

        fun getInstance(context: Context): LetsMovieDataBase? {
            if (INSTANCE == null) {
                synchronized(LetsMovieDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            LetsMovieDataBase::class.java, DATABASE_NAME
                    )
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