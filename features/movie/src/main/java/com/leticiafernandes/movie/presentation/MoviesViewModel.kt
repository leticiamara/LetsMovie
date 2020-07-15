package com.leticiafernandes.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leticiafernandes.movie.domain.model.Movie
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
        private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _uiState = MutableLiveData<MoviesUiState>()
    val uiState: LiveData<MoviesUiState>
        get() = _uiState
    //private val movies: MutableLiveData<List<Movie>> = MutableLiveData()

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

    //fun getMovies(): LiveData<List<Movie>> = movies

    private fun showMovies(movieList: List<Movie>) {
        _uiState.value = Success(movieList)
    }
}
