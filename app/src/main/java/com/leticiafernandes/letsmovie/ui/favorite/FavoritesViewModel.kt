package com.leticiafernandes.letsmovie.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leticiafernandes.letsmovie.domain.usecase.ObserveFavoritesUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleFavoriteUseCase
import com.leticiafernandes.letsmovie.domain.usecase.ToggleResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val observeFavoritesUseCase: ObserveFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<FavoritesUiState>(FavoritesUiState.Loading)
    val uiState: LiveData<FavoritesUiState> get() = _uiState

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        observeFavoritesUseCase()
            .onEach { favorites ->
                _uiState.value = if (favorites.isEmpty()) {
                    FavoritesUiState.Empty
                } else {
                    FavoritesUiState.Content(favorites.map { it.toItem() })
                }
            }
            .catch { throwable ->
                _uiState.value = FavoritesUiState.Error(
                    throwable.message ?: "Unexpected error loading favorites."
                )
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(movieId: Long) {
        viewModelScope.launch {
            when (val result = toggleFavoriteUseCase(movieId)) {
                is ToggleResult.Error -> _uiState.value = FavoritesUiState.Error(result.message)
                ToggleResult.Added, ToggleResult.Removed -> {

                }
            }
        }
    }
}