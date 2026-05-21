package com.leticiafernandes.letsmovie.data.remote

import com.leticiafernandes.letsmovie.data.remote.dto.GuestSessionDTO

interface AuthRemoteDataSource {
    suspend fun createGuestSession(): GuestSessionDTO
}
