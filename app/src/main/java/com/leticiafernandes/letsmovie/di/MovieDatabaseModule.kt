package com.leticiafernandes.letsmovie.di

import android.content.Context
import androidx.room.Room
import com.leticiafernandes.letsmovie.data.local.LetsMovieDataBase
import com.leticiafernandes.letsmovie.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): LetsMovieDataBase =
        Room.databaseBuilder(
            context,
            LetsMovieDataBase::class.java,
            LetsMovieDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideFavoriteMovieDao(database: LetsMovieDataBase): FavoriteMovieDao =
        database.favoriteMovieDao()
}
