package com.leticiafernandes.movies.di

import com.leticiafernandes.movies.data.datasource.remote.MoviesRemoteDataSource
import com.leticiafernandes.movies.data.datasource.remote.MoviesRemoteDataSourceImpl
import com.leticiafernandes.movies.data.repository.MoviesRepository
import com.leticiafernandes.movies.data.repository.MoviesRepositoryImpl
import com.leticiafernandes.movies.domain.usecase.MoviesUseCase
import com.leticiafernandes.movies.domain.usecase.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class MoviesBuilderModule {

    @Binds
    abstract fun bindMoviesUseCase(moviesUseCaseImpl: MoviesUseCaseImpl): MoviesUseCase

    @Binds
    abstract fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun bindMoviesRemoteDataSource(moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource
}
