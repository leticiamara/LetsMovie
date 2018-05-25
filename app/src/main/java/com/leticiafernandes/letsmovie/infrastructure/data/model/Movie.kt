package com.leticiafernandes.letsmovie.infrastructure.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by leticiafernandes on 20/05/18.
 */
class Movie(var id: Long, @SerializedName("vote_count") var voteCount: Int, var title: String,
            var video: Boolean? = null, @SerializedName("vote_average") var voteAverage: Double,
            var popularity: Double, @SerializedName("poster_path") var posterPath: String,
            @SerializedName("original_language") var originalLanguage: String,
            @SerializedName("original_title") var originalTitle: String,
            @SerializedName("genre_ids") var genreIds: List<Long>,
            @SerializedName("backdrop_path") var backdropPath: String,
            var adult: Boolean, var overview: String,
            @SerializedName("release_date") var releaseDate: Date) {
}