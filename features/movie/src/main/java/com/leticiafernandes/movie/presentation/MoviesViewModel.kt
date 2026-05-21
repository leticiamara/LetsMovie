package com.leticiafernandes.movie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.movie.domain.usecase.MoviesUseCase
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
            try {
                val result = moviesUseCase.listPopularMovies(pageNumber)
                totalPages = result.totalPages
                showMovies(result.results)
            } catch (e: Exception) {
                showError(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun listNextPage() {
        pageNumber = pageNumber.inc()
        if (pageNumber <= totalPages) {
            listPopularMovies(pageNumber)
        }
    }

    private fun showMovies(movieList: List<com.leticiafernandes.movie.presentation.model.MovieItem>) {
        _uiState.value = Success(movieList)
    }

    private fun showError(throwable: Throwable) {
        _uiState.value = Error(throwable.message)
    }
}
