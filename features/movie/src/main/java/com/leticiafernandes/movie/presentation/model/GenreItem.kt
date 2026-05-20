package com.leticiafernandes.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreItem(
        val id: Long,
        val name: String
) : Parcelable
