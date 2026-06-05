package com.leticiafernandes.letsmovie.ui.login

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.fake.FakeAuthRepository
import com.leticiafernandes.letsmovie.login.domain.CreateGuestSessionUseCase
import com.leticiafernandes.letsmovie.login.ui.SplashViewModel
import com.leticiafernandes.letsmovie.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var authRepository: FakeAuthRepository

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
    }

    private fun createViewModel() = SplashViewModel(
        createGuestSessionUseCase = CreateGuestSessionUseCase(authRepository)
    )

    @Test
    fun `when not logged in, creates guest session and becomes ready`() = runTest {
        authRepository.result = NetworkResult.Success(Unit)

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.ready.value)
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
