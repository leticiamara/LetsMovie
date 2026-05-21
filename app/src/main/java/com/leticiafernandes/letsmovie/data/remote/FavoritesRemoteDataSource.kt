package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.dto.FavoriteMovieDTO

interface FavoritesRemoteDataSource {
    suspend fun fetchMovieDetails(movieId: Long): FavoriteMovieDTO
}
