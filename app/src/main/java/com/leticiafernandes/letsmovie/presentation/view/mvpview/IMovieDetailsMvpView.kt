package com.leticiafernandes.letsmovie.presentation.view.mvpview

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import com.leticiafernandes.letsmovie.infrastructure.model.Movie

/**
 * Created by leticiafernandes on 26/05/18.
 */
interface IMovieDetailsMvpView {

    fun getContext(): Context
    fun showMovieDetails(movie: Movie)
    fun setBackdrop(bitmapDrawable: BitmapDrawable)
}