package com.leticiafernandes.letsmovie.ui.login

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.domain.usecase.CreateGuestSessionUseCase
import com.leticiafernandes.letsmovie.fake.FakeAuthRepository
import com.leticiafernandes.letsmovie.fake.FakeSessionManager
import com.leticiafernandes.letsmovie.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var authRepository: FakeAuthRepository
    private lateinit var sessionManager: FakeSessionManager

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        sessionManager = FakeSessionManager()
    }

    private fun createViewModel() = SplashViewModel(
        createGuestSessionUseCase = CreateGuestSessionUseCase(authRepository, sessionManager),
        sessionManager = sessionManager
    )

    @Test
    fun `when not logged in, creates guest session and becomes ready`() = runTest {
        authRepository.result = NetworkResult.Success("session-123")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.ready.value)
        assertEquals("session-123", sessionManager.guestSessionId)
    }

    @Test
    fun `when already logged in, skips session creation and becomes ready`() = runTest {
        sessionManager.guestSessionId = "existing-session"

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.ready.value)
        assertEquals("existing-session", sessionManager.guestSessionId)
    }

    @Test
    fun `when session creation fails with HttpError, still becomes ready`() = runTest {
        authRepository.result = NetworkResult.HttpError(500, "Server Error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.ready.value)
    }

    @Test
    fun `when session creation fails with NetworkError, still becomes ready`() = runTest {
        authRepository.result = NetworkResult.NetworkError(RuntimeException("no internet"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.ready.value)
    }
}
