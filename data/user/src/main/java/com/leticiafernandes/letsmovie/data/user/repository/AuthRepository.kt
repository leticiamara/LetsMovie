package com.leticiafernandes.letsmovie.data.user.repository

import com.leticiafernandes.letsmovie.core.network.NetworkResult

interface AuthRepository {
    suspend fun createGuestSession(): NetworkResult<Unit>

    fun isUserLoggedIn(): Boolean
}
