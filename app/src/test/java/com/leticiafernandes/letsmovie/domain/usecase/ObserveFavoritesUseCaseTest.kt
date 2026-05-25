package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.fake.FakeFavoritesRepository
import com.leticiafernandes.letsmovie.fake.buildFakeMovie
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ObserveFavoritesUseCaseTest {

    private lateinit var repository: FakeFavoritesRepository
    private lateinit var observeFavoritesUseCase: ObserveFavoritesUseCase

    @Before
    fun setUp() {
        repository = FakeFavoritesRepository()
        observeFavoritesUseCase = ObserveFavoritesUseCase(repository)
    }

    @Test
    fun `when repository has no favorites, emits empty list`() = runTest {
        val result = observeFavoritesUseCase().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `when repository has favorites, emits them`() = runTest {
        val movie1 = buildFakeMovie(1L)
        val movie2 = buildFakeMovie(2L)
        repository.fillFavorites(movie1, movie2)

        val result = observeFavoritesUseCase().first()

        assertEquals(2, result.size)
        assertEquals(movie1, result[0])
        assertEquals(movie2, result[1])
    }

    @Test
    fun `emits updated list when favorites change`() = runTest {
        val results = mutableListOf<Int>()
        val flow = observeFavoritesUseCase()

        repository.fillFavorites()
        results.add(flow.first().size)

        repository.fillFavorites(buildFakeMovie(1L))
        results.add(flow.first().size)

        assertEquals(listOf(0, 1), results)
    }
}
