package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.Date

class FakeFavoritesRepository : FavoritesRepository {

    private val favorites = MutableStateFlow<List<FavoriteMovie>>(emptyList())

    var addFavoriteResult: NetworkResult<Unit> = NetworkResult.Success(Unit)

    override fun observeFavorites(): Flow<List<FavoriteMovie>> = favorites

    override fun observeIsFavorite(movieId: Long): Flow<Boolean> =
        favorites.map { list -> list.any { it.id == movieId } }

    override suspend fun addFavorite(movieId: Long): NetworkResult<Unit> {
        if (addFavoriteResult is NetworkResult.Success) {
            favorites.value += buildFakeMovie(movieId)
        }
        return addFavoriteResult
    }

    override suspend fun removeFavorite(movieId: Long) {
        favorites.value = favorites.value.filter { it.id != movieId }
    }

    fun fillFavorites(vararg movies: FavoriteMovie) {
        favorites.value = movies.toList()
    }
}

fun buildFakeMovie(id: Long) = FavoriteMovie(
    id = id,
    title = "Movie $id",
    voteAverage = 7.0,
    posterPath = null,
    backdropPath = null,
    overview = "Overview $id",
    releaseDate = Date(0)
)
