package com.leticiafernandes.letsmovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
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
            _uiState.value = when (val result = moviesUseCase.listMovieDetails(movieId)) {
                is NetworkResult.Success -> ShowMovieInfo(result.data)
                is NetworkResult.HttpError -> ShowMovieError("Server error ${result.code}: ${result.message}")
                is NetworkResult.NetworkError -> ShowMovieError("Network error. Please check your connection.")
            }
        }
    }
}
