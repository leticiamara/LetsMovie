package com.leticiafernandes.movie.presentation.detail

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import coil.request.GetRequest
import coil.request.LoadRequest
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.ProgressItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableLiveData<MovieDetailUiState>()
    val uiState: LiveData<MovieDetailUiState>
        get() = _uiState

    fun showMovieDetails(movie: MovieItem?) {
        if (movie != null) {
            _uiState.value = ShowMovieInfo(movie)
        } else {
            _uiState.value = ShowMovieEmptyState
        }
    }
}
