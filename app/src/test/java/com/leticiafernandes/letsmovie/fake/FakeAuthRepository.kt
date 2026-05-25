package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.remote.NetworkResult
import com.leticiafernandes.letsmovie.data.repository.AuthRepository

class FakeAuthRepository : AuthRepository {

    var result: NetworkResult<String> = NetworkResult.Success("fake-session-id")

    override suspend fun createGuestSession(): NetworkResult<String> = result
}
