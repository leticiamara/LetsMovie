package com.leticiafernandes.letsmovie.domain.interactor

import com.leticiafernandes.letsmovie.infrastructure.data.entity.Movie
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieResponse
import com.leticiafernandes.letsmovie.infrastructure.service.IMovieService
import com.leticiafernandes.letsmovie.infrastructure.service.RetrofitHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesInteractor : IMoviesInteractor {

    override fun listPopularMovies() : Observable<MovieResponse> {
        val movieService = RetrofitHelper().getRetrofit().create(IMovieService::class.java)
        return movieService.listPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
