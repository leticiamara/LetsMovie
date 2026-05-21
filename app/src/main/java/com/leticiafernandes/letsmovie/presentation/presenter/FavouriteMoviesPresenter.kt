package com.leticiafernandes.letsmovie.presentation.presenter

import android.content.Context
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.infrastructure.persistence.LetsMovieDataBase
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteMoviesPresenter(var context: Context, private var favouriteMvpView: IFavouriteMvpView) :
        IFavouriteMoviesPresenter {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var database: LetsMovieDataBase? = null

    init {
        database = LetsMovieDataBase.getInstance(context)
    }

    override fun listAllFavouriteMovies() {
        scope.launch {
            val movies = withContext(Dispatchers.IO) {
                database?.movieDao()?.getAll() ?: emptyList<Movie>()
            }
            favouriteMvpView.listMovies(movies)
        }
    }

    fun clear() {
        scope.cancel()
    }
}
