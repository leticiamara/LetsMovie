package com.leticiafernandes.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.ProgressItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val FIRST_PAGE = 1

class MoviesViewModel @Inject constructor(
        private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _uiState = MutableLiveData<MoviesUiState>()
    val uiState: LiveData<MoviesUiState>
        get() = _uiState
    var isLoading = false
    private var pageNumber: Int = FIRST_PAGE
    private var totalPages: Int = FIRST_PAGE

    fun listPopularMovies(pageNumber: Int = FIRST_PAGE) {
        moviesUseCase.listPopularMovies(pageNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doOnTerminate { hideLoading() }
                .subscribe({
                    showMovies(it.results)
                    totalPages = it.totalPages
                }, {
                    showError(it)
                }).apply {
                    compositeDisposable.add(this)
                }
    }

    fun listNextPage() {
        pageNumber = pageNumber.inc()
        if (pageNumber <= totalPages) {
            _uiState.value = ShowMovieListProgress(ProgressItem)
            listPopularMovies(pageNumber)
        }
    }

    private fun showLoading() {
        isLoading = true
        _uiState.value = Loading(true)
    }

    private fun hideLoading() {
        isLoading = false
        _uiState.value = HideMovieListProgress
        _uiState.value = Loading(false)
    }

    private fun showMovies(movieList: List<MovieItem>) {
        _uiState.value = Success(movieList)
    }

    private fun showError(throwable: Throwable) {
        _uiState.value = Error
    }
}
