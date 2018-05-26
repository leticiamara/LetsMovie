package com.leticiafernandes.letsmovie.presentation.presenter

import android.content.Context
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.domain.interactor.IMoviesInteractor
import com.leticiafernandes.letsmovie.domain.interactor.MoviesInteractor
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.infrastructure.persistence.LetsMovieDataBase
import com.leticiafernandes.letsmovie.infrastructure.model.MovieResponse
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMoviesMvpView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesPresenter(var context: Context, val movieMvpView: IMoviesMvpView) : IMoviesPresenter {

    var moviesInteractor: IMoviesInteractor = MoviesInteractor()
    var genres: Map<Long, String> = HashMap()
    private var database: LetsMovieDataBase? = null

    init {
        database = LetsMovieDataBase.getInstance(context)
    }

    override fun listPopularMovies() {
        moviesInteractor.listAllGenres()
                .flatMap { genreResponse ->
                    genres = genreResponse.genres.associateBy({it.id}, {it.name})
                    return@flatMap moviesInteractor.listPopularMovies()
                }
                .subscribe({ movieList: MovieResponse? ->
                    run {
                        movieMvpView.showPopularMovieList(movieList?.results)
                    }
                }, { throwable: Throwable? -> run { movieMvpView.showMessage(throwable?.message.toString()) } })
    }

    override fun addMovieToFavouriteList(favouriteMovie: Movie) {
        favouriteMovie.favourite = true

        Single.fromCallable {
            database?.movieDao()?.insert(favouriteMovie)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe { _ ->
            run {
                movieMvpView.showMessage(R.string.movie_added_to_favourite_list)
            }
        }
    }

}