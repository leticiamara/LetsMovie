package com.leticiafernandes.letsmovie.data.user.datasource.remote

import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun createGuestSession(): GuestSessionDTO =
        authService.createGuestSession()
}
