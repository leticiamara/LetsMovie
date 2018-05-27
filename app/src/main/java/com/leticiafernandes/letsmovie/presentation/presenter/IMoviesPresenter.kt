package com.leticiafernandes.letsmovie.presentation.presenter

import com.leticiafernandes.letsmovie.infrastructure.model.Movie

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface IMoviesPresenter {
    fun listPopularMovies()
    fun addMovieToFavouriteList(favouriteMovie: Movie)
    fun listNextPage()
}