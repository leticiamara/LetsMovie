package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import com.leticiafernandes.letsmovie.domain.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<FavoriteMovie>> = repository.observeFavorites()
}
