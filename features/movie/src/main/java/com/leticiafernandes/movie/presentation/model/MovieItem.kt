package com.leticiafernandes.movie.presentation.model

import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieItem(
        val id: Long,
        val voteCount: Int,
        val title: String,
        val video: Boolean? = null,
        val voteAverage: Double,
        val popularity: Double,
        val posterPath: String?,
        val originalLanguage: String,
        val originalTitle: String,
        val genreIds: List<Long>,
        val backdropPath: String?,
        val adult: Boolean,
        val overview: String,
        val releaseDate: Date,
        val genres: List<GenreItem>,
        var favourite: Boolean = false
) : PagingItem
