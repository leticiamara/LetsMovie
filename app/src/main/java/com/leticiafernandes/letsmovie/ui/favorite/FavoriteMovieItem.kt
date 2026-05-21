package com.leticiafernandes.letsmovie.ui.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class FavoriteMovieItem(
    val id: Long,
    val title: String,
    val voteAverage: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: Date
) : Parcelable