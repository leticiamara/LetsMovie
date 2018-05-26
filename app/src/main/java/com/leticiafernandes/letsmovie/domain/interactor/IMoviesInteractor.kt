package com.leticiafernandes.letsmovie.domain.interactor

import com.leticiafernandes.letsmovie.infrastructure.model.GenreResponse
import com.leticiafernandes.letsmovie.infrastructure.model.MovieResponse
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface IMoviesInteractor {

    fun listPopularMovies() : Observable<MovieResponse>
    fun listAllGenres(): Observable<GenreResponse>
    fun loadBackdropImage(imageName: String): Observable<ResponseBody>
}