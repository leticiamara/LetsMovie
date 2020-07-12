package com.leticiafernandes.movies.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leticiafernandes.movies.domain.model.Movie
import com.leticiafernandes.movies.domain.usecase.MoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel @ViewModelInject constructor(
        private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun listPopularMovies() {
        moviesUseCase.listPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showMovies(it.results)
                }, {}).apply {
                    compositeDisposable.add(this)
                }
    }

    fun getMovies(): LiveData<List<Movie>> = movies

    private fun showMovies(movieList: List<Movie>) {
        movies.value = movieList
    }
}