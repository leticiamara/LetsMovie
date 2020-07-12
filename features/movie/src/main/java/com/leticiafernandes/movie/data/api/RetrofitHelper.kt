package com.leticiafernandes.movies.data.api

import com.leticiafernandes.letsmovie.presentation.view.AppApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val MOVIE_BASE_URL = "https://api.themoviedb.org/"
private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB

object RetrofitHelper {

    fun getRetrofit(): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()

        val cacheDirectory = AppApplication.getInstance()?.applicationContext?.cacheDir
        cacheDirectory?.let {
            val cache = Cache(it, CACHE_SIZE.toLong())
            okHttpBuilder.cache(cache)
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = okHttpBuilder
                .addInterceptor(interceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(MOVIE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}