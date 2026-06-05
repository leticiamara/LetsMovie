package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeAuthRepository
import com.leticiafernandes.letsmovie.login.domain.CreateGuestSessionUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreateGuestSessionUseCaseTest {

    private lateinit var authRepository: FakeAuthRepository
    private lateinit var createGuestSessionUseCase: CreateGuestSessionUseCase

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        createGuestSessionUseCase = CreateGuestSessionUseCase(authRepository)
    }

    @Test
    fun `when createGuestSession succeeds, returns Success`() = runTest {
        authRepository.result = NetworkResult.Success(Unit)

        val result = createGuestSessionUseCase()

        assertEquals(NetworkResult.Success(Unit), result)
    }

    @Test
    fun `when createGuestSession returns HttpError, returns HttpError`() = runTest {
        authRepository.result = NetworkResult.HttpError(401, "Unauthorized")

        val result = createGuestSessionUseCase()

        assertEquals(NetworkResult.HttpError(401, "Unauthorized"), result)
    }

    @Test
    fun `when createGuestSession returns NetworkError, returns NetworkError`() = runTest {
        val exception = RuntimeException("no internet")
        authRepository.result = NetworkResult.NetworkError(exception)

        val result = createGuestSessionUseCase()

        assertTrue(result is NetworkResult.NetworkError)
    }
}
