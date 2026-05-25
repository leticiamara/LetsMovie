package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.dto.GenreDTO
import com.leticiafernandes.letsmovie.fake.FakeGenresRemoteDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
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
    fun `listAllGenres returns Success with mapped domain genres`() {
        val genreAction = "Action"
        val genreAdventure = "Adventure"
        runTest {
            remoteDataSource.result = listOf(
                GenreDTO(id = 28L, name = genreAction),
                GenreDTO(id = 12L, name = genreAdventure)
            )

            val result = repository.listAllGenres()

            assertTrue(result is NetworkResult.Success)
            val genres = (result as NetworkResult.Success).data
            assertEquals(2, genres.size)
            assertEquals(28L, genres[0].id)
            assertEquals(genreAction, genres[0].name)
            assertEquals(12L, genres[1].id)
            assertEquals(genreAdventure, genres[1].name)
        }
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
        remoteDataSource.exception = HttpException(Response.error<Unit>(500, "".toResponseBody(null)))

        val result = repository.listAllGenres()

        assertTrue(result is NetworkResult.HttpError)
        assertEquals(500, (result as NetworkResult.HttpError).code)
    }
}
