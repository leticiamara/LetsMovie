package com.leticiafernandes.letsmovie.ui.movie

import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem

sealed class MovieDetailUiState
data class ShowMovieInfo(val movie: MovieItem) : MovieDetailUiState()
data class ShowMovieError(val message: String) : MovieDetailUiState()
object ShowMovieEmptyState : MovieDetailUiState()
