package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.fake.FakeFavoritesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsFavoriteUseCaseTest {

    private lateinit var repository: FakeFavoritesRepository
    private lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @Before
    fun setUp() {
        repository = FakeFavoritesRepository()
        isFavoriteUseCase = IsFavoriteUseCase(repository)
    }

    @Test
    fun `when movie is not in favorites, emits false`() = runTest {
        val result = isFavoriteUseCase(movieId = 1L).first()

        assertFalse(result)
    }

    @Test
    fun `when movie is in favorites, emits true`() = runTest {
        repository.fillFavorites(buildFakeMovie(1L))
        val result = isFavoriteUseCase(movieId = 1L).first()

        assertTrue(result)
    }

    @Test
    fun `emits false for a different movie id even when favorites are not empty`() = runTest {
        repository.fillFavorites(buildFakeMovie(1L))
        val result = isFavoriteUseCase(movieId = 99L).first()

        assertFalse(result)
    }
}
