package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.remote.GenresRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.GenresRemoteDataSourceImpl
import com.leticiafernandes.letsmovie.data.repository.GenresRepository
import com.leticiafernandes.letsmovie.data.repository.GenresRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GenresModule {

    @Binds
    abstract fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository

    @Binds
    abstract fun bindGenresRemoteDataSource(
            genresRemoteDataSourceImpl: GenresRemoteDataSourceImpl
    ): GenresRemoteDataSource
}
