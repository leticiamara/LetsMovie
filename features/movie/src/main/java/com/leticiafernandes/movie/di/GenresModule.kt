package com.leticiafernandes.movie.di

import com.leticiafernandes.movie.data.datasource.remote.GenresRemoteDataSource
import com.leticiafernandes.movie.data.datasource.remote.GenresRemoteDataSourceImpl
import com.leticiafernandes.movie.data.repository.GenresRepository
import com.leticiafernandes.movie.data.repository.GenresRepositoryImpl
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
