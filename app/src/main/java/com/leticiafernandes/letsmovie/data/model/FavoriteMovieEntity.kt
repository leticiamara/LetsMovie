package com.leticiafernandes.letsmovie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Long,
    val title: String,
    val voteAverage: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val releaseDate: Date,
    val favoriteAt: Date = Date()
)