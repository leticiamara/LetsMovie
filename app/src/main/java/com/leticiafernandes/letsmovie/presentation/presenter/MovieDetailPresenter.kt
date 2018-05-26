package com.leticiafernandes.letsmovie.presentation.presenter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import com.leticiafernandes.letsmovie.domain.interactor.IMoviesInteractor
import com.leticiafernandes.letsmovie.domain.interactor.MoviesInteractor
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMovieDetailsMvpView

/**
 * Created by leticiafernandes on 26/05/18.
 */
class MovieDetailPresenter(var mvpView: IMovieDetailsMvpView) : IMovieDetailPresenter {

    var moviesInteractor: IMoviesInteractor = MoviesInteractor()

    override fun loadImageFromURL(backdropPath: String) {
        moviesInteractor.loadBackdropImage(backdropPath).subscribe { responseBody ->
            run {
                val bitmap = BitmapFactory.decodeStream(responseBody.byteStream())
                val bitmapDrawable = BitmapDrawable(mvpView.getContext().resources, bitmap)
                bitmapDrawable.alpha = 0xCC
                mvpView.setBackdrop(bitmapDrawable)
            }
        }
    }
}