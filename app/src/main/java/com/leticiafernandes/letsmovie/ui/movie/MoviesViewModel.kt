package com.leticiafernandes.letsmovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST_PAGE = 1

@HiltViewModel
class MoviesViewModel @Inject constructor(
        private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<MoviesUiState>()
    val uiState: LiveData<MoviesUiState> get() = _uiState
    var isLoading = false
    private var pageNumber: Int = FIRST_PAGE
    private var totalPages: Int = FIRST_PAGE

    fun listPopularMovies(pageNumber: Int = FIRST_PAGE) {
        isLoading = true
        _uiState.value = Loading(true)
        viewModelScope.launch {
            when (val result = moviesUseCase.listPopularMovies(pageNumber)) {
                is NetworkResult.Success -> {
                    totalPages = result.data.totalPages
                    _uiState.value = Success(result.data.results)
                }
                is NetworkResult.HttpError ->
                    _uiState.value = Error("Server error ${result.code}: ${result.message}")
                is NetworkResult.NetworkError ->
                    _uiState.value = Error("Network error. Please check your connection.")
            }
            isLoading = false
        }
    }

    fun listNextPage() {
        pageNumber = pageNumber.inc()
        if (pageNumber <= totalPages) {
            listPopularMovies(pageNumber)
        }
    }
}
