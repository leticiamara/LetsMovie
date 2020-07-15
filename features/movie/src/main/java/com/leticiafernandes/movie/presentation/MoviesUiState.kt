package com.leticiafernandes.movie.presentation

import com.leticiafernandes.movie.domain.model.Movie

sealed class MoviesUiState
object Loading : MoviesUiState()
data class Success(val moviesList: List<Movie>) : MoviesUiState()
object Error : MoviesUiState()
