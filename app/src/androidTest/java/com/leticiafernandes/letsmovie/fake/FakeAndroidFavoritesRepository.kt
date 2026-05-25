package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date

class FakeAndroidFavoritesRepository(
    private val throwOnObserve: Throwable? = null
) : FavoritesRepository {

    private val favorites = MutableStateFlow<List<FavoriteMovie>>(emptyList())

    override fun observeFavorites(): Flow<List<FavoriteMovie>> =
        if (throwOnObserve != null) flow { throw throwOnObserve } else favorites

    override fun observeIsFavorite(movieId: Long): Flow<Boolean> =
        favorites.map { list -> list.any { it.id == movieId } }

    override suspend fun addFavorite(movieId: Long): NetworkResult<Unit> {
        favorites.value += buildAndroidFakeMovie(movieId)
        return NetworkResult.Success(Unit)
    }

    override suspend fun removeFavorite(movieId: Long) {
        favorites.value = favorites.value.filter { it.id != movieId }
    }

    fun seed(vararg movies: FavoriteMovie) {
        favorites.value = movies.toList()
    }
}

fun buildAndroidFakeMovie(id: Long, title: String = "Movie $id") = FavoriteMovie(
    id = id,
    title = title,
    voteAverage = 7.0,
    posterPath = null,
    backdropPath = null,
    overview = "Overview $id",
    releaseDate = Date(0)
)
