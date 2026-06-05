package com.leticiafernandes.letsmovie.login.domain

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.data.user.repository.AuthRepository
import javax.inject.Inject

class CreateGuestSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): NetworkResult<Unit> = authRepository.createGuestSession()
}
