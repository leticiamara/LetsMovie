package com.leticiafernandes.letsmovie.data.remote.api

import com.leticiafernandes.letsmovie.data.remote.dto.GenreResultDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieDTO
import com.leticiafernandes.letsmovie.data.remote.dto.MovieResultDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/movie/popular")
    suspend fun listPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieResultDTO

    @GET("/3/genre/movie/list")
    suspend fun listAllGenres(
        @Query("language") language: String = "en-US"
    ): GenreResultDTO

    @GET("/3/movie/{movie_id}")
    suspend fun listMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = "en-US"
    ): MovieDTO
}
