package com.leticiafernandes.movies.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieResultDTO(
        val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        val results: List<MovieDTO>
)
