package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.local.FavoritesLocalDataSource
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.Date

class FakeFavoritesLocalDataSource : FavoritesLocalDataSource {

    private val entities = MutableStateFlow<List<FavoriteMovieEntity>>(emptyList())
    val upsertedEntities = mutableListOf<FavoriteMovieEntity>()
    val deletedIds = mutableListOf<Long>()

    override fun observeAll(): Flow<List<FavoriteMovieEntity>> = entities

    override fun observeIsFavorite(movieId: Long): Flow<Boolean> =
        entities.map { list -> list.any { it.movieId == movieId } }

    override suspend fun upsert(entity: FavoriteMovieEntity) {
        upsertedEntities += entity
        entities.value = entities.value.filter { it.movieId != entity.movieId } + entity
    }

    override suspend fun deleteById(movieId: Long) {
        deletedIds += movieId
        entities.value = entities.value.filter { it.movieId != movieId }
    }

    fun seed(vararg items: FavoriteMovieEntity) {
        entities.value = items.toList()
    }
}

fun buildFakeEntity(id: Long = 1L, title: String = "Movie $id") = FavoriteMovieEntity(
    movieId = id,
    title = title,
    voteAverage = 7.0,
    posterPath = null,
    backdropPath = null,
    overview = "Overview $id",
    releaseDate = Date(0)
)
