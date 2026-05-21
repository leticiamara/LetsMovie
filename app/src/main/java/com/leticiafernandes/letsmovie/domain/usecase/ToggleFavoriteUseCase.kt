package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository,
    private val isFavoriteUseCase: IsFavoriteUseCase
) {

    suspend operator fun invoke(movieId: Long): ToggleResult {
        val currentlyFavorite = isFavoriteUseCase(movieId).first()

        return if (currentlyFavorite) {
            repository.removeFavorite(movieId)
            ToggleResult.Removed
        } else {
            when (val result = repository.addFavorite(movieId)) {
                is NetworkResult.Success -> ToggleResult.Added
                is NetworkResult.HttpError -> ToggleResult.Error("Server error ${result.code}")
                is NetworkResult.NetworkError -> ToggleResult.Error("Network error. Please check your connection.")
            }
        }
    }
}

sealed class ToggleResult {
    object Added : ToggleResult()
    object Removed : ToggleResult()
    data class Error(val message: String) : ToggleResult()
}
