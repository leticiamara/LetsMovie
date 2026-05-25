package com.leticiafernandes.letsmovie.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.leticiafernandes.letsmovie.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.Locale

private const val MOVIE_BASE_URL = "https://api.themoviedb.org/"

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

object RetrofitHelper {

    fun getRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val urlWithKey = originalRequest.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()
            chain.proceed(originalRequest.newBuilder().url(urlWithKey).build())
        }

        val localeInterceptor = Interceptor { chain ->
            val locale = Locale.getDefault()
            val language = if (locale.country.isNotEmpty()) {
                "${locale.language}-${locale.country}"
            } else {
                locale.language
            }
            val originalRequest = chain.request()
            val urlWithLocale = originalRequest.url.newBuilder()
                .setQueryParameter("language", language)
                .build()
            chain.proceed(originalRequest.newBuilder().url(urlWithLocale).build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(localeInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
