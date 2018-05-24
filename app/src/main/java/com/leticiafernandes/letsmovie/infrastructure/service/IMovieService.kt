package com.leticiafernandes.letsmovie.infrastructure.service

import com.leticiafernandes.letsmovie.infrastructure.data.entity.GenreResponse
import com.leticiafernandes.letsmovie.infrastructure.data.entity.Movie
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface IMovieService {

    @GET("/3/movie/popular")
    fun listPopularMovies(@Query("api_key") apiKey: String = "95ba8e4e85f5add6b0de44a9e213ef31",
                          @Query("language") language: String = "en-US",
                          @Query("page") page: Int = 1) : Observable<MovieResponse>

    @GET("/3/genre/movie/list")
    fun listAllGenres(@Query("api_key") apiKey: String = "95ba8e4e85f5add6b0de44a9e213ef31",
                      @Query("language") language: String = "en-US") : Observable<GenreResponse>
}