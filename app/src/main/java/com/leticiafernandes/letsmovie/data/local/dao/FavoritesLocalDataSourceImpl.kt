package com.leticiafernandes.letsmovie.data.local.dao

import com.leticiafernandes.letsmovie.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoritesLocalDataSource {

    override fun observeAll(): Flow<List<FavoriteMovieEntity>> =
        favoriteMovieDao.observeAll()

    override suspend fun findById(id: Long): FavoriteMovieEntity? =
        favoriteMovieDao.findById(id)

    override fun observeIsFavorite(id: Long): Flow<Boolean> =
        favoriteMovieDao.isFavorite(id)

    override suspend fun upsert(entity: FavoriteMovieEntity) =
        favoriteMovieDao.upsert(entity)

    override suspend fun deleteById(id: Long) =
        favoriteMovieDao.deleteById(id)
}
