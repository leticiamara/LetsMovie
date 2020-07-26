package com.leticiafernandes.letsmovie.infrastructure.api

import com.leticiafernandes.letsmovie.presentation.view.AppApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
    }
}
