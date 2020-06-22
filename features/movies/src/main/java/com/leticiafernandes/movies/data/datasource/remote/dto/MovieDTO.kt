package com.leticiafernandes.movies.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieDTO(
        val id: Long,
        @SerializedName("vote_count") val voteCount: Int,
        val title: String,
        val video: Boolean? = null,
        @SerializedName("vote_average") val voteAverage: Double,
        val popularity: Double,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("genre_ids") val genreIds: List<Long>,
        @SerializedName("backdrop_path") val backdropPath: String,
        val adult: Boolean,
        val overview: String,
        @SerializedName("release_date") var releaseDate: Date,
        val genres: String
)