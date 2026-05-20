package com.leticiafernandes.movie.di

import com.leticiafernandes.movie.data.api.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitHelper.getRetrofit()
    }
}
