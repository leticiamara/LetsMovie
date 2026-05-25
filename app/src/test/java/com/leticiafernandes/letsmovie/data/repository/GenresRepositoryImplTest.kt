package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO
import com.leticiafernandes.letsmovie.fake.FakeGenresRemoteDataSource
import com.leticiafernandes.letsmovie.util.buildHttpException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GenresRepositoryImplTest {

    private lateinit var remoteDataSource: FakeGenresRemoteDataSource
    private lateinit var repository: GenresRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = FakeGenresRemoteDataSource()
        repository = GenresRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `listAllGenres returns Success with mapped domain genres`() = runTest {
        remoteDataSource.result = listOf(
            GenreDTO(id = 28L, name = "Action"),
            GenreDTO(id = 12L, name = "Adventure")
        )

        val result = repository.listAllGenres()

        assertTrue(result is NetworkResult.Success)
        val genres = (result as NetworkResult.Success).data
        assertEquals(2, genres.size)
        assertEquals(28L, genres[0].id)
        assertEquals("Action", genres[0].name)
        assertEquals(12L, genres[1].id)
        assertEquals("Adventure", genres[1].name)
    }

    @Test
    fun `listAllGenres returns Success with empty list when remote returns empty`() = runTest {
        remoteDataSource.result = emptyList()

        val result = repository.listAllGenres()

        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isEmpty())
    }

    @Test
    fun `listAllGenres returns NetworkError when IOException is thrown`() = runTest {
        remoteDataSource.exception = IOException("No connection")

        val result = repository.listAllGenres()

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `listAllGenres returns HttpError when HttpException is thrown`() = runTest {
        remoteDataSource.exception = buildHttpException(500)

        val result = repository.listAllGenres()

        assertTrue(result is NetworkResult.HttpError)
        assertEquals(500, (result as NetworkResult.HttpError).code)
    }
}
