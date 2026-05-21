package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.remote.api.RetrofitHelper
import com.leticiafernandes.letsmovie.data.remote.api.FavoritesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesNetworkModule {

    @Provides
    @Singleton
    @Named("favoritesRetrofit")
    fun provideRetrofit(): Retrofit = RetrofitHelper.getRetrofit()

    @Provides
    fun provideFavoritesService(@Named("favoritesRetrofit") retrofit: Retrofit): FavoritesService =
        retrofit.create(FavoritesService::class.java)
}
