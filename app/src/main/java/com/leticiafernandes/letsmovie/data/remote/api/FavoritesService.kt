package com.leticiafernandes.letsmovie.data.remote.api

import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface FavoritesService {

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Long
    ): FavoriteMovieDTO
}