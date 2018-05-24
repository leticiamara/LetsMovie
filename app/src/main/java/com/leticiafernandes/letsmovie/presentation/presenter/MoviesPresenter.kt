package com.leticiafernandes.letsmovie.presentation.presenter

import com.leticiafernandes.letsmovie.domain.interactor.IMoviesInteractor
import com.leticiafernandes.letsmovie.domain.interactor.MoviesInteractor
import com.leticiafernandes.letsmovie.infrastructure.data.entity.MovieResponse
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMoviesMvpView

/**
 * Created by leticiafernandes on 20/05/18.
 */
class MoviesPresenter(val movieMvpView: IMoviesMvpView) : IMoviesPresenter {

    var moviesInteractor: IMoviesInteractor = MoviesInteractor()
    var genres: Map<Long, String> = HashMap<Long, String>()

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

    override fun listFavouriteMovies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}