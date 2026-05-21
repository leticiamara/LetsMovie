package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.remote.AuthRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.AuthRemoteDataSourceImpl
import com.leticiafernandes.letsmovie.data.repository.AuthRepository
import com.leticiafernandes.letsmovie.data.repository.AuthRepositoryImpl
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
}
