package com.leticiafernandes.letsmovie.ui.favorite

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    object Empty : FavoritesUiState()
    data class Content(val favorites: List<FavoriteMovieItem>) : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
}