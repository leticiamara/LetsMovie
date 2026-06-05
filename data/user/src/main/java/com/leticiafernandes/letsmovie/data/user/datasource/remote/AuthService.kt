package com.leticiafernandes.letsmovie.data.user.datasource.remote

import retrofit2.http.GET

interface AuthService {

    @GET("/3/authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionDTO
}
