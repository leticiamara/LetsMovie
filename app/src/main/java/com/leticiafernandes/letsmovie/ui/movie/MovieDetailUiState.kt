package com.leticiafernandes.letsmovie.ui.movie

import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem

sealed class MovieDetailUiState
object ShowMovieLoading : MovieDetailUiState()
data class ShowMovieInfo(val movie: MovieItem) : MovieDetailUiState()
sealed class ShowMovieError : MovieDetailUiState() {
    data class Http(val code: Int) : ShowMovieError()
    object Network : ShowMovieError()
}
object ShowMovieEmptyState : MovieDetailUiState()
