package com.leticiafernandes.letsmovie.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: Long,
    @SerialName("vote_count") val voteCount: Int = 0,
    val title: String = "",
    val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    val popularity: Double = 0.0,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("original_language") val originalLanguage: String = "",
    @SerialName("original_title") val originalTitle: String = "",
    @SerialName("genre_ids") val genreIds: List<Long>? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    val adult: Boolean = false,
    val overview: String = "",
    @SerialName("release_date") val releaseDate: String = "",
    val genres: List<GenreDTO>? = null
)
