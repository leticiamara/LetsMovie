package com.leticiafernandes.movie.presentation.model

data class MovieResultItem(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<MovieItem>
)
