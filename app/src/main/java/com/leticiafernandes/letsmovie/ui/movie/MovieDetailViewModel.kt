package com.leticiafernandes.letsmovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.IsFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FLOW_STOP_TIMEOUT = 5000L

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesUseCase: MoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    isFavoriteUseCase: IsFavoriteUseCase,
) : ViewModel() {

    private val _movieId: Long? = savedStateHandle["movieId"]
    private val _uiState = MutableLiveData<MovieDetailUiState>()
    val uiState: LiveData<MovieDetailUiState> = _uiState

    val isBookmarked: StateFlow<Boolean> = _movieId?.let { id ->
        isFavoriteUseCase(id).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(FLOW_STOP_TIMEOUT),
            initialValue = false
        )
    } ?: MutableStateFlow(false)

    init {
        fetchMovieDetails()
    }

    fun fetchMovieDetails() {
        val movieId = _movieId ?: run {
            _uiState.value = ShowMovieEmptyState
            return
        }
        viewModelScope.launch {
            _uiState.value = ShowMovieLoading
            _uiState.value = when (val result = moviesUseCase.listMovieDetails(movieId)) {
                is NetworkResult.Success -> ShowMovieInfo(result.data)
                is NetworkResult.HttpError -> ShowMovieError.Http(result.code)
                is NetworkResult.NetworkError -> ShowMovieError.Network
            }
        }
    }

    fun toggleWatchlist() {
        val movieId = _movieId ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(movieId)
        }
    }
}
