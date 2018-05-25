package com.leticiafernandes.letsmovie.infrastructure.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by leticiafernandes on 24/05/18.
 */
@Entity(tableName = "movie")
data class MovieEntity(@PrimaryKey(autoGenerate = true) var id: Long,
                  @ColumnInfo(name = "themoviedb_id") var theMovieDbId: Long,
                  @ColumnInfo(name ="vote_count") var voteCount: Int, var title: String,
                  var video: Boolean?, @ColumnInfo(name = "vote_average") var voteAverage: Double,
                  var popularity: Double, @ColumnInfo(name = "poster_path") var posterPath: String,
                  @ColumnInfo(name = "original_language") var originalLanguage: String,
                  @ColumnInfo(name = "original_title") var originalTitle: String,
                  @Ignore @ColumnInfo(name = "genre_ids") var genreIds: List<Long>,
                  @ColumnInfo(name = "backdrop_path") var backdropPath: String,
                  var adult: Boolean, var overview: String,
                  @Ignore @ColumnInfo(name = "release_date") var releaseDate: Date) {

    constructor() : this(0,0,0,"",null,0.0,0.0,"","","", listOf(),"",false,"",Date())
}