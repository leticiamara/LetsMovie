package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeFavoritesLocalDataSource
import com.leticiafernandes.letsmovie.fake.FakeFavoritesRemoteDataSource
import com.leticiafernandes.letsmovie.fake.buildFakeEntity
import com.leticiafernandes.letsmovie.fake.buildFakeFavoriteMovieDTO
import com.leticiafernandes.letsmovie.util.buildHttpException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class FavoritesRepositoryImplTest {

    private lateinit var localDataSource: FakeFavoritesLocalDataSource
    private lateinit var remoteDataSource: FakeFavoritesRemoteDataSource
    private lateinit var repository: FavoritesRepositoryImpl

    @Before
    fun setUp() {
        localDataSource = FakeFavoritesLocalDataSource()
        remoteDataSource = FakeFavoritesRemoteDataSource()
        repository = FavoritesRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `observeFavorites emits empty list when no favorites stored`() = runTest {
        val result = repository.observeFavorites().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `observeFavorites maps entities to domain models`() = runTest {
        localDataSource.seed(
            buildFakeEntity(id = 1L, title = "Inception"),
            buildFakeEntity(id = 2L, title = "Interstellar")
        )

        val result = repository.observeFavorites().first()

        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals("Inception", result[0].title)
        assertEquals(2L, result[1].id)
        assertEquals("Interstellar", result[1].title)
    }

    @Test
    fun `observeIsFavorite emits true when movie is in favorites`() = runTest {
        localDataSource.seed(buildFakeEntity(id = 5L))

        val result = repository.observeIsFavorite(5L).first()

        assertTrue(result)
    }

    @Test
    fun `observeIsFavorite emits false when movie is not in favorites`() = runTest {
        val result = repository.observeIsFavorite(99L).first()

        assertFalse(result)
    }

    @Test
    fun `addFavorite fetches from remote and upserts to local`() = runTest {
        remoteDataSource.result = buildFakeFavoriteMovieDTO(id = 7L, title = "The Dark Knight")

        repository.addFavorite(7L)

        assertEquals(1, localDataSource.upsertedEntities.size)
        assertEquals(7L, localDataSource.upsertedEntities[0].movieId)
        assertEquals("The Dark Knight", localDataSource.upsertedEntities[0].title)
    }

    @Test
    fun `addFavorite returns Success on success`() = runTest {
        remoteDataSource.result = buildFakeFavoriteMovieDTO(id = 1L)

        val result = repository.addFavorite(1L)

        assertTrue(result is NetworkResult.Success)
    }

    @Test
    fun `addFavorite returns HttpError when remote throws HttpException`() = runTest {
        remoteDataSource.exception = buildHttpException(403)

        val result = repository.addFavorite(1L)

        assertTrue(result is NetworkResult.HttpError)
        assertEquals(403, (result as NetworkResult.HttpError).code)
    }

    @Test
    fun `addFavorite returns NetworkError when remote throws IOException`() = runTest {
        remoteDataSource.exception = IOException("No connection")

        val result = repository.addFavorite(1L)

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `addFavorite does not upsert to local when remote throws`() = runTest {
        remoteDataSource.exception = IOException("No connection")

        repository.addFavorite(1L)

        assertTrue(localDataSource.upsertedEntities.isEmpty())
    }

    @Test
    fun `removeFavorite deletes movie from local data source`() = runTest {
        localDataSource.seed(buildFakeEntity(id = 3L))

        repository.removeFavorite(3L)

        assertEquals(listOf(3L), localDataSource.deletedIds)
        assertTrue(repository.observeFavorites().first().isEmpty())
    }
}
