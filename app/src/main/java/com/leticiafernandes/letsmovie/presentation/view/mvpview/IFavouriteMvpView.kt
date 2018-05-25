package com.leticiafernandes.letsmovie.presentation.view.mvpview

import com.leticiafernandes.letsmovie.infrastructure.model.Movie

/**
 * Created by leticiafernandes on 24/05/18.
 */
interface IFavouriteMvpView {

    fun showMessage(resId: Int)
    fun listMovies(movies: List<Movie>?)
}