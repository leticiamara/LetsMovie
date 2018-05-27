package com.leticiafernandes.letsmovie.domain.interactor

import com.leticiafernandes.letsmovie.infrastructure.api.IMovieService
import com.leticiafernandes.letsmovie.infrastructure.api.RetrofitHelper
import com.leticiafernandes.letsmovie.infrastructure.model.GenreResponse
import com.leticiafernandes.letsmovie.infrastructure.model.MovieResponse
import com.leticiafernandes.letsmovie.infrastructure.persistence.dao.GenreDao
import com.leticiafernandes.letsmovie.presentation.util.MovieImageUtils.Companion.BASE_URL
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesInteractor : IMoviesInteractor {

    override fun listPopularMovies(genreDao: GenreDao, page: Int): Observable<MovieResponse> {
        val movieService = RetrofitHelper().getRetrofit().create(IMovieService::class.java)
        return movieService.listPopularMovies(page = page)
                .flatMap({ movieResponse ->
                    run {
                        movieResponse.results.map { movie ->
                            movie.genres = genreDao.findByIds(movie.genreIds).joinToString(transform = { it.name })
                        }
                        return@flatMap Observable.fromArray(movieResponse)
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun listAllGenres(): Observable<GenreResponse> {
        val movieService = RetrofitHelper().getRetrofit().create(IMovieService::class.java)
        return movieService.listAllGenres()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loadBackdropImage(imageName: String): Observable<ResponseBody> {
        val movieService = RetrofitHelper().getRetrofit().create(IMovieService::class.java)
        return movieService.getImageBackdrop(BASE_URL + imageName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
