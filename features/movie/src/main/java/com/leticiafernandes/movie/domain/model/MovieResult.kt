package com.leticiafernandes.movie.domain.model

data class MovieResult(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<Movie>
)
