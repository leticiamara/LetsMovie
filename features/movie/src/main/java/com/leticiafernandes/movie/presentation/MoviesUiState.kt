package com.leticiafernandes.movie.presentation

import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.ProgressItem

sealed class MoviesUiState
data class Loading(val loading: Boolean) : MoviesUiState()
data class Success(val moviesList: List<MovieItem>) : MoviesUiState()
data class ShowMovieListProgress(val progressItem: ProgressItem) : MoviesUiState()
object HideMovieListProgress : MoviesUiState()
data class Error(val errorMessage: String?) : MoviesUiState()
