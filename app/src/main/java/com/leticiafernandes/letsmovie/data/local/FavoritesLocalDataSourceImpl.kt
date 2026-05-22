package com.leticiafernandes.letsmovie.data.local

import com.leticiafernandes.letsmovie.data.local.dao.FavoriteMovieDao
import com.leticiafernandes.letsmovie.data.model.FavoriteMovieData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) : FavoritesLocalDataSource {

    override fun observeAll(): Flow<List<FavoriteMovieData>> =
        favoriteMovieDao.observeAll()

    override suspend fun findById(id: Long): FavoriteMovieData? =
        favoriteMovieDao.findById(id)

    override fun observeIsFavorite(id: Long): Flow<Boolean> =
        favoriteMovieDao.isFavorite(id)

    override suspend fun upsert(entity: FavoriteMovieData) =
        favoriteMovieDao.upsert(entity)

    override suspend fun deleteById(id: Long) =
        favoriteMovieDao.deleteById(id)
}