package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.FavoritesRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO

class FakeFavoritesRemoteDataSource : FavoritesRemoteDataSource {

    var result: FavoriteMovieDTO = buildFakeFavoriteMovieDTO()
    var exception: Exception? = null

    override suspend fun fetchMovieDetails(movieId: Long): FavoriteMovieDTO {
        exception?.let { throw it }
        return result
    }
}

fun buildFakeFavoriteMovieDTO(
    id: Long = 1L,
    title: String = "Movie $id"
) = FavoriteMovieDTO(
    id = id,
    title = title,
    voteAverage = 7.5,
    posterPath = null,
    backdropPath = null,
    overview = "Overview $id",
    releaseDate = "2010-07-16"
)
