package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeFavoritesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var repository: FakeFavoritesRepository
    private lateinit var isFavoriteUseCase: IsFavoriteUseCase
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        repository = FakeFavoritesRepository()
        isFavoriteUseCase = IsFavoriteUseCase(repository)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(repository, isFavoriteUseCase)
    }

    @Test
    fun `when movie is not favorite, invoke adds it and returns Added`() = runTest {
        val result = toggleFavoriteUseCase(movieId = 1L)

        assertEquals(ToggleResult.Added, result)
        assertTrue(repository.observeIsFavorite(1L).first())
    }

    @Test
    fun `when movie is already favorite, invoke removes it and returns Removed`() = runTest {
        repository.fillFavorites(buildFakeMovie(1L))
        val result = toggleFavoriteUseCase(movieId = 1L)

        assertEquals(ToggleResult.Removed, result)
        assertFalse(repository.observeIsFavorite(1L).first())
    }

    @Test
    fun `when addFavorite returns HttpError, invoke returns Error with server message`() = runTest {
        repository.addFavoriteResult = NetworkResult.HttpError(404, "Not Found")
        val result = toggleFavoriteUseCase(movieId = 2L)

        assertEquals(ToggleResult.Error("Server error 404"), result)
    }

    @Test
    fun `when addFavorite returns NetworkError, invoke returns Error with network message`() = runTest {
        repository.addFavoriteResult = NetworkResult.NetworkError(RuntimeException("timeout"))
        val result = toggleFavoriteUseCase(movieId = 3L)

        assertEquals(ToggleResult.Error("Network error. Please check your connection."), result)
    }

    @Test
    fun `when addFavorite fails, movie is not added to favorites`() = runTest {
        repository.addFavoriteResult = NetworkResult.HttpError(500, "Server Error")
        toggleFavoriteUseCase(movieId = 4L)

        assertFalse(repository.observeIsFavorite(4L).first())
    }
}
