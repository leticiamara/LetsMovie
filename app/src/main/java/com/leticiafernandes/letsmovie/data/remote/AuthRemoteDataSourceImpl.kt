package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.api.AuthService
import com.leticiafernandes.letsmovie.data.remote.dto.GuestSessionDTO
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun createGuestSession(): GuestSessionDTO =
        authService.createGuestSession()
}
