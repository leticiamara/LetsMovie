package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeAuthRepository
import com.leticiafernandes.letsmovie.fake.FakeSessionManager
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreateGuestSessionUseCaseTest {

    private lateinit var authRepository: FakeAuthRepository
    private lateinit var sessionManager: FakeSessionManager
    private lateinit var createGuestSessionUseCase: CreateGuestSessionUseCase

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        sessionManager = FakeSessionManager()
        createGuestSessionUseCase = CreateGuestSessionUseCase(authRepository, sessionManager)
    }

    @Test
    fun `when createGuestSession succeeds, saves session id and returns Success`() = runTest {
        authRepository.result = NetworkResult.Success("session-abc")

        val result = createGuestSessionUseCase()

        assertEquals(NetworkResult.Success(Unit), result)
        assertEquals("session-abc", sessionManager.guestSessionId)
        assertTrue(sessionManager.isLoggedIn)
    }

    @Test
    fun `when createGuestSession returns HttpError, session id is not saved`() = runTest {
        authRepository.result = NetworkResult.HttpError(401, "Unauthorized")

        val result = createGuestSessionUseCase()

        assertEquals(NetworkResult.HttpError(401, "Unauthorized"), result)
        assertNull(sessionManager.guestSessionId)
    }

    @Test
    fun `when createGuestSession returns NetworkError, session id is not saved`() = runTest {
        val exception = RuntimeException("no internet")
        authRepository.result = NetworkResult.NetworkError(exception)

        val result = createGuestSessionUseCase()

        assertTrue(result is NetworkResult.NetworkError)
        assertNull(sessionManager.guestSessionId)
    }
}
