package com.leticiafernandes.letsmovie.ui.watchlist

sealed class WatchlistUiState {
    object Loading : WatchlistUiState()
    object Empty : WatchlistUiState()
    data class Content(val favorites: List<FavoriteMovieItem>) : WatchlistUiState()
    data class Error(val message: String) : WatchlistUiState()
}