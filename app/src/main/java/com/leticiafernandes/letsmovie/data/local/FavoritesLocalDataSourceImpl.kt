package com.leticiafernandes.letsmovie.data.local

import com.leticiafernandes.letsmovie.data.local.dao.FavoriteMovieDao
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoritesLocalDataSource {

    override fun observeAll(): Flow<List<FavoriteMovieEntity>> =
        favoriteMovieDao.observeAll()

    override fun observeIsFavorite(movieId: Long): Flow<Boolean> =
        favoriteMovieDao.isFavorite(movieId)

    override suspend fun upsert(entity: FavoriteMovieEntity) =
        favoriteMovieDao.upsert(entity)

    override suspend fun deleteById(movieId: Long) =
        favoriteMovieDao.deleteById(movieId)
}