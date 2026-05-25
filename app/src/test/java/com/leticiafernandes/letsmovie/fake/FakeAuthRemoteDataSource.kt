package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.AuthRemoteDataSource
import com.leticiafernandes.letsmovie.data.remote.dto.GuestSessionDTO

class FakeAuthRemoteDataSource : AuthRemoteDataSource {

    var result: GuestSessionDTO = GuestSessionDTO(success = true, guestSessionId = "test-session-id")
    var exception: Exception? = null

    override suspend fun createGuestSession(): GuestSessionDTO {
        exception?.let { throw it }
        return result
    }
}
