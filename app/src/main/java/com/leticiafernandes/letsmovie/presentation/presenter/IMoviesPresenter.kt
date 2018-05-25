package com.leticiafernandes.letsmovie.presentation.presenter

import java.util.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
interface IMoviesPresenter {
    fun listPopularMovies()
    fun addMovieToFavouriteList(
            theMovieDbId: Long, voteCount: Int, title: String, video: Boolean?, voteAverage: Double,
            popularity: Double, posterPath: String, originalLanguage: String, originalTitle: String,
            genreIds: List<Long>, backdropPath: String, adult: Boolean, overview: String,
            releaseDate: Date)
}