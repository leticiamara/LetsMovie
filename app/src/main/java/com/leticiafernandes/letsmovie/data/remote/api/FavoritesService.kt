package com.leticiafernandes.letsmovie.data.remote.api

import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoritesService {

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "en-US"
    ): FavoriteMovieDTO
}