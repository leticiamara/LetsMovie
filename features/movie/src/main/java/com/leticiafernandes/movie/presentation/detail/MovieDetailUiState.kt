package com.leticiafernandes.movie.presentation.detail

import com.leticiafernandes.movie.presentation.model.MovieItem
import com.leticiafernandes.movie.presentation.model.ProgressItem

sealed class MovieDetailUiState
data class ShowMovieInfo(val movie: MovieItem) : MovieDetailUiState()
object ShowMovieEmptyState : MovieDetailUiState()
