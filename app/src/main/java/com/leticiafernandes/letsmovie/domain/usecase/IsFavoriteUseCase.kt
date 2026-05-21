package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(movieId: Long): Flow<Boolean> =
        repository.observeIsFavorite(movieId)
}
