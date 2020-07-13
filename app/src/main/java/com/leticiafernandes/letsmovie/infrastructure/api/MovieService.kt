package com.leticiafernandes.letsmovie.infrastructure.api

import com.leticiafernandes.letsmovie.infrastructure.model.GenreResponse
import com.leticiafernandes.letsmovie.infrastructure.model.MovieResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface MovieService {

    @GET("/3/movie/popular")
    fun listPopularMovies(@Query("api_key") apiKey: String = "95ba8e4e85f5add6b0de44a9e213ef31",
                          @Query("language") language: String = "en-US",
                          @Query("page") page: Int = 1) : Observable<MovieResponse>

    @GET("/3/genre/movie/list")
    fun listAllGenres(@Query("api_key") apiKey: String = "95ba8e4e85f5add6b0de44a9e213ef31",
                      @Query("language") language: String = "en-US") : Observable<GenreResponse>

    @GET
    fun getImageBackdrop(@Url imageUrl: String) : Observable<ResponseBody>
}