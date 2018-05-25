package com.leticiafernandes.letsmovie.presentation.presenter

import android.content.Context
import com.leticiafernandes.letsmovie.infrastructure.persistence.LetsMovieDataBase
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by leticiafernandes on 24/05/18.
 */
class FavouriteMoviesPresenter(var context: Context, private var favouriteMvpView: IFavouriteMvpView) :
        IFavouriteMoviesPresenter {

    private var database: LetsMovieDataBase? = null

    init {
        database = LetsMovieDataBase.getInstance(context)
    }

    override fun listAllFavouriteMovies() {
        Single.fromCallable {
            database?.movieDao()?.getAll()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe { movies ->
            run {
                favouriteMvpView.listMovies(movies)
            }
        }
    }
}