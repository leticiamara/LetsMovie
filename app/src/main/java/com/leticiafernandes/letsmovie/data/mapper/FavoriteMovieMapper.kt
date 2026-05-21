package com.leticiafernandes.letsmovie.data.mapper

import com.leticiafernandes.letsmovie.data.local.entity.FavoriteMovieEntity
import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val releaseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

private fun parseReleaseDate(value: String): Date =
    if (value.isNotEmpty()) runCatching { releaseDateFormat.parse(value)!! }.getOrDefault(Date(0))
    else Date(0)

fun FavoriteMovieDTO.toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = parseReleaseDate(releaseDate)
)

fun FavoriteMovieEntity.toDomain(): FavoriteMovie = FavoriteMovie(
    id = id,
    title = title,
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = releaseDate
)

fun FavoriteMovie.toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = id,
    title = title,
    voteAverage = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = releaseDate
)
