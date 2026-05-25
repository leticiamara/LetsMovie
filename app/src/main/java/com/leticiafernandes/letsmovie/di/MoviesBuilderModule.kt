package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.remote.MoviesRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.MoviesRemoteDataSourceImpl
import com.leticiafernandes.letsmovie.data.repository.MoviesRepository
import com.leticiafernandes.letsmovie.data.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesBuilderModule {

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindMoviesRemoteDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource
}
