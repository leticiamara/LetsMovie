package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.user.datasource.local.AuthLocalDataSource
import com.leticiafernandes.letsmovie.data.user.datasource.local.AuthLocalDataSourceImpl
import com.leticiafernandes.letsmovie.data.user.datasource.remote.AuthRemoteDataSource
import com.leticiafernandes.letsmovie.data.user.datasource.remote.AuthRemoteDataSourceImpl
import com.leticiafernandes.letsmovie.data.user.repository.AuthRepository
import com.leticiafernandes.letsmovie.data.user.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindingsModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun bindAuthLocalDataSource(impl: AuthLocalDataSourceImpl): AuthLocalDataSource
}
