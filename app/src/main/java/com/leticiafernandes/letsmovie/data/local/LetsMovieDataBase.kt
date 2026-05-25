package com.leticiafernandes.letsmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leticiafernandes.letsmovie.data.local.converter.DateConverter
import com.leticiafernandes.letsmovie.data.local.converter.LongListConverter
import com.leticiafernandes.letsmovie.data.local.dao.FavoriteMovieDao
import com.leticiafernandes.letsmovie.data.local.dao.GenreDao
import com.leticiafernandes.letsmovie.data.local.dao.MovieDao
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieEntity
import com.leticiafernandes.letsmovie.data.model.Genre
import com.leticiafernandes.letsmovie.data.model.Movie

@Database(
    entities = [Movie::class, Genre::class, FavoriteMovieEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class, LongListConverter::class)
abstract class LetsMovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {
        const val DATABASE_NAME = "letsmovie.db"
    }
}
