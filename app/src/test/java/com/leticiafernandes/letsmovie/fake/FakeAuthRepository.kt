package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.data.user.repository.AuthRepository

class FakeAuthRepository : AuthRepository {

    var result: NetworkResult<Unit> = NetworkResult.Success(Unit)

    override suspend fun createGuestSession(): NetworkResult<Unit> = result

    override fun isUserLoggedIn(): Boolean = false
}
