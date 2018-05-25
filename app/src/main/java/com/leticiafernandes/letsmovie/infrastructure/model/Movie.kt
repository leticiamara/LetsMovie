package com.leticiafernandes.letsmovie.infrastructure.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.leticiafernandes.letsmovie.infrastructure.persistence.converters.DateConverter
import com.leticiafernandes.letsmovie.infrastructure.persistence.converters.LongListConverter
import java.util.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
@Entity(tableName = "movie", primaryKeys = arrayOf("id"))
@TypeConverters(LongListConverter::class, DateConverter::class)
data class Movie(
        var id: Long,
        @field:SerializedName("vote_count") var voteCount: Int,
        var title: String,
        var video: Boolean? = null,
        @field:SerializedName("vote_average") var voteAverage: Double,
        var popularity: Double,
        @field:SerializedName("poster_path") var posterPath: String,
        @field:SerializedName("original_language") var originalLanguage: String,
        @field:SerializedName("original_title") var originalTitle: String,
        @field:SerializedName("genre_ids") var genreIds: List<Long>,
        @field:SerializedName("backdrop_path") var backdropPath: String,
        var adult: Boolean,
        var overview: String,
        @field:SerializedName("release_date") var releaseDate: Date) {

    var favourite: Boolean = false
}