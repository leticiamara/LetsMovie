package com.leticiafernandes.movie.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leticiafernandes.movie.presentation.model.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
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
