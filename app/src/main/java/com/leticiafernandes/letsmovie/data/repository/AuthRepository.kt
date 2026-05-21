package com.leticiafernandes.letsmovie.data.repository

import com.leticiafernandes.letsmovie.data.remote.NetworkResult

interface AuthRepository {
    suspend fun createGuestSession(): NetworkResult<String>
}
