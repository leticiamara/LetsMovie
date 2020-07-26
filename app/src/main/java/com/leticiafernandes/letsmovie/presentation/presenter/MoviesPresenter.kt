package com.leticiafernandes.letsmovie.presentation.presenter

import android.content.Context
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.domain.interactor.IMoviesInteractor
import com.leticiafernandes.letsmovie.domain.interactor.MoviesInteractor
import com.leticiafernandes.letsmovie.infrastructure.model.GenreResponse
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.infrastructure.model.MovieResponse
import com.leticiafernandes.letsmovie.infrastructure.persistence.LetsMovieDataBase
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMoviesMvpView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesPresenter(var context: Context, val movieMvpView: IMoviesMvpView) : IMoviesPresenter {

    var moviesInteractor: IMoviesInteractor = MoviesInteractor()
    private var database: LetsMovieDataBase? = null
    private var nextPage: Int = 1

    init {
        database = LetsMovieDataBase.getInstance(context)
    }

    override fun listPopularMovies() {
        Observable.concat(getAllGenresObservable(), listPopularMoviesObservable()).subscribe()
    }

    override fun listNextPage() {
        nextPage = nextPage.inc()
        moviesInteractor.listPopularMovies(database?.genreDao()!!, nextPage)
                .subscribe({ movieList: MovieResponse? ->
            run {
                movieMvpView.showNextPage(movieList?.results)
            }
        }, { throwable: Throwable? -> run { movieMvpView.showMessage(throwable?.message.toString()) } })
    }

    override fun addMovieToFavoriteList(favouriteMovie: Movie) {
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

    private fun getAllGenresObservable(): Observable<GenreResponse> {
        return moviesInteractor.listAllGenres()
                .doOnNext({ genreResponse ->
                    Single.fromCallable {
                        database?.genreDao()?.insert(genreResponse.genres)
                    }.subscribeOn(Schedulers.io())
                            .subscribe()
                })
    }

    private fun listPopularMoviesObservable(): Observable<MovieResponse> {
        return moviesInteractor.listPopularMovies(database?.genreDao()!!)
                .doOnNext({ movieResponse ->
                    movieMvpView.showPopularMovieList(movieResponse.results)
                })
    }

}