package com.leticiafernandes.movie.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
import com.leticiafernandes.movie.presentation.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    var moviesUseCase: MoviesUseCase,
) : ViewModel() {

    private val _movieId: Long? = savedStateHandle["movieId"]
    private val _uiState = MutableLiveData<MovieDetailUiState>()
    val uiState: LiveData<MovieDetailUiState> = _uiState
    private val disposables = CompositeDisposable()

    init {
        fetchMovieDetails()
    }

    fun fetchMovieDetails() {
        if (_movieId != null) {
            moviesUseCase.listMovieDetails(_movieId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.value = ShowMovieInfo(it)
                }, {
                    _uiState.value = ShowMovieEmptyState
                    it.printStackTrace()
                }).also { disposables.add(it) }
        } else {
            _uiState.value = ShowMovieEmptyState
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
