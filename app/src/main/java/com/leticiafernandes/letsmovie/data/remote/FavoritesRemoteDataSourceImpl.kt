package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.api.FavoritesService
import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO
import javax.inject.Inject

class FavoritesRemoteDataSourceImpl @Inject constructor(
    private val favoritesService: FavoritesService
) : FavoritesRemoteDataSource {

    override suspend fun fetchMovieDetails(movieId: Long): FavoriteMovieDTO =
        favoritesService.fetchMovieDetails(movieId)
}
