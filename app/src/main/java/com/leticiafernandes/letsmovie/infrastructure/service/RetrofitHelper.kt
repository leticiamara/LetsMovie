package com.leticiafernandes.letsmovie.infrastructure.service

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.leticiafernandes.letsmovie.presentation.view.AppApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by leticiafernandes on 20/05/18.
 */
class RetrofitHelper {

    private val END_POINT = "https://api.themoviedb.org/"

    fun getRetrofit(): Retrofit {

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(AppApplication.getInstance()?.applicationContext?.cacheDir,
                cacheSize.toLong())

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cache(cache)
                .build()

        return Retrofit.Builder()
                .baseUrl(END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}