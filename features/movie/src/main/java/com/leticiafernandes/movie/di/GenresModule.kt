package com.leticiafernandes.movies.di

import com.leticiafernandes.movies.data.datasource.remote.GenresRemoteDataSource
import com.leticiafernandes.movies.data.datasource.remote.GenresRemoteDataSourceImpl
import com.leticiafernandes.movies.data.repository.GenresRepository
import com.leticiafernandes.movies.data.repository.GenresRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class GenresModule {

    @Binds
    abstract fun bindGenresRepository(genresRepositoryImpl: GenresRepositoryImpl): GenresRepository

    @Binds
    abstract fun bindGenresRemoteDataSource(
            genresRemoteDataSourceImpl: GenresRemoteDataSourceImpl
    ): GenresRemoteDataSource
}
