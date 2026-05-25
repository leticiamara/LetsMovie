package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.dto.GuestSessionDTO
import com.leticiafernandes.letsmovie.fake.FakeAuthRemoteDataSource
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AuthRepositoryImplTest {

    private lateinit var remoteDataSource: FakeAuthRemoteDataSource
    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = FakeAuthRemoteDataSource()
        repository = AuthRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `createGuestSession returns Success with session id from dto`() = runTest {
        remoteDataSource.result = GuestSessionDTO(success = true, guestSessionId = "abc-123")

        val result = repository.createGuestSession()

        assertTrue(result is NetworkResult.Success)
        assertEquals("abc-123", (result as NetworkResult.Success).data)
    }

    @Test
    fun `createGuestSession returns NetworkError when IOException is thrown`() = runTest {
        remoteDataSource.exception = IOException("No connection")

        val result = repository.createGuestSession()

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `createGuestSession returns HttpError when HttpException is thrown`() = runTest {
        remoteDataSource.exception = HttpException(Response.error<Unit>(401, "".toResponseBody(null)))

        val result = repository.createGuestSession()

        assertTrue(result is NetworkResult.HttpError)
        assertEquals(401, (result as NetworkResult.HttpError).code)
    }
}
