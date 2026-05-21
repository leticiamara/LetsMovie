package com.leticiafernandes.letsmovie.ui.movie.model

data class MovieResultItem(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<MovieItem>
)
