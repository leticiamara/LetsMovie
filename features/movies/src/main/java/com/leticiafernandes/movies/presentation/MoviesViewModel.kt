package com.leticiafernandes.movies.presentation

import androidx.lifecycle.ViewModel
import com.leticiafernandes.movies.domain.usecase.MoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(private val moviesUseCase: MoviesUseCase) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun listPopularMovies() {
        moviesUseCase.listPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {}).apply {
                    compositeDisposable.add(this)
                }
    }
}