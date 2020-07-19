package com.leticiafernandes.movie.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
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
        val genres: List<Genre>,
        var favourite: Boolean = false
) : Parcelable
