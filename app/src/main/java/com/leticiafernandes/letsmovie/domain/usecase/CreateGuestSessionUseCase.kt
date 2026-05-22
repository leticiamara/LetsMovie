package com.leticiafernandes.letsmovie.domain.usecase

import com.leticiafernandes.letsmovie.data.local.SessionPreferencesManager
import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.remote.getOrElse
import com.leticiafernandes.letsmovie.data.repository.AuthRepository
import javax.inject.Inject

class CreateGuestSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionPreferencesManager
) {
    suspend operator fun invoke(): NetworkResult<Unit> {
        val guestSessionId = authRepository.createGuestSession().getOrElse { return it }
        sessionManager.guestSessionId = guestSessionId
        return NetworkResult.Success(Unit)
    }
}
