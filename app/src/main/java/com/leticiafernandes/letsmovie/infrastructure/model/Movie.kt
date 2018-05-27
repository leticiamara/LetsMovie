package com.leticiafernandes.letsmovie.infrastructure.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
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
        @field:SerializedName("release_date") var releaseDate: Date) : Parcelable {
    var favourite: Boolean = false

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readInt(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readDouble(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readString(),
            ArrayList<Long>().apply { source.readList(this, Long::class.java.classLoader) },
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readSerializable() as Date
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeInt(voteCount)
        writeString(title)
        writeValue(video)
        writeDouble(voteAverage)
        writeDouble(popularity)
        writeString(posterPath)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeList(genreIds)
        writeString(backdropPath)
        writeInt((if (adult) 1 else 0))
        writeString(overview)
        writeSerializable(releaseDate)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}