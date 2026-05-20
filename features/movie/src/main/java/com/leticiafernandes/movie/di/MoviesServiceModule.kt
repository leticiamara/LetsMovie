package com.leticiafernandes.movie.di

import com.leticiafernandes.movie.data.api.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MoviesServiceModule {

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}
