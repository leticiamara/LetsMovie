package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.local.FavoritesLocalDataSource
import com.leticiafernandes.letsmovie.data.local.FavoritesLocalDataSourceImpl
import com.leticiafernandes.letsmovie.data.remote.FavoritesRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.FavoritesRemoteDataSourceImpl
import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import com.leticiafernandes.letsmovie.data.repository.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesBuilderModule {

    @Binds
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    abstract fun bindFavoritesLocalDataSource(impl: FavoritesLocalDataSourceImpl): FavoritesLocalDataSource

    @Binds
    abstract fun bindFavoritesRemoteDataSource(impl: FavoritesRemoteDataSourceImpl): FavoritesRemoteDataSource
}
