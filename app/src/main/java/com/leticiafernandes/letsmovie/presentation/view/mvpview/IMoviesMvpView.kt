package com.leticiafernandes.letsmovie.presentation.view.mvpview

import com.leticiafernandes.letsmovie.infrastructure.model.Movie

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface IMoviesMvpView {
    fun showPopularMovieList(movieList: List<Movie>?)

    fun showMessage(resource: Int)

    fun showMessage(message: String)

    fun showNextPage(results: List<Movie>?)
}