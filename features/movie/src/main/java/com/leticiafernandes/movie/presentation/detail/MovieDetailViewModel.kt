package com.leticiafernandes.movie.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesUseCase: MoviesUseCase,
) : ViewModel() {

    private val _movieId: Long? = savedStateHandle["movieId"]
    private val _uiState = MutableLiveData<MovieDetailUiState>()
    val uiState: LiveData<MovieDetailUiState> = _uiState

    init {
        fetchMovieDetails()
    }

    fun fetchMovieDetails() {
        val movieId = _movieId ?: run {
            _uiState.value = ShowMovieEmptyState
            return
        }
        viewModelScope.launch {
            try {
                _uiState.value = ShowMovieInfo(moviesUseCase.listMovieDetails(movieId))
            } catch (e: Exception) {
                _uiState.value = ShowMovieEmptyState
                e.printStackTrace()
            }
        }
    }
}
