package com.leticiafernandes.letsmovie.data.remote.api

import com.leticiafernandes.letsmovie.data.remote.dto.GuestSessionDTO
import retrofit2.http.GET

interface AuthService {

    @GET("/3/authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionDTO
}
