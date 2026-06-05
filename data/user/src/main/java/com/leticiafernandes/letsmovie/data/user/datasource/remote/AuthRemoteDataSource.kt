package com.leticiafernandes.letsmovie.data.user.datasource.remote

interface AuthRemoteDataSource {
    suspend fun createGuestSession(): GuestSessionDTO
}
