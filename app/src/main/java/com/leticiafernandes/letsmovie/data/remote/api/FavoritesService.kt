package com.leticiafernandes.letsmovie.data.remote.api

import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoritesService {

    @GET("/3/movie/{movie_id}")
    suspend fun fetchMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): FavoriteMovieDTO

    companion object {
        // Em um projeto real, mova essa chave para o BuildConfig / variáveis de ambiente.
        private const val TMDB_API_KEY = "95ba8e4e85f5add6b0de44a9e213ef31"
    }
}