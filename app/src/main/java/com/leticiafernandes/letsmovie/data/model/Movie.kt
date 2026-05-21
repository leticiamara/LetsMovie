package com.leticiafernandes.letsmovie.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import com.leticiafernandes.letsmovie.data.local.DateConverter
import com.leticiafernandes.letsmovie.data.local.LongListConverter
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "movie", primaryKeys = ["id"])
@TypeConverters(LongListConverter::class, DateConverter::class)
@Parcelize
data class Movie(
    var id: Long,
    var voteCount: Int,
    var title: String,
    var video: Boolean? = null,
    var voteAverage: Double,
    var popularity: Double,
    var posterPath: String?,
    var originalLanguage: String,
    var originalTitle: String,
    var genreIds: List<Long>,
    var backdropPath: String?,
    var adult: Boolean,
    var overview: String,
    var releaseDate: Date?,
    var genres: String
) : Parcelable {
    var favourite: Boolean = false
}
