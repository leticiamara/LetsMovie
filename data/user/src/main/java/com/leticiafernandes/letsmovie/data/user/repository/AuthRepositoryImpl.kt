package com.leticiafernandes.letsmovie.data.user.repository

import com.leticiafernandes.letsmovie.core.network.NetworkResult
import com.leticiafernandes.letsmovie.core.network.safeApiCall
import com.leticiafernandes.letsmovie.data.user.datasource.local.AuthLocalDataSource
import com.leticiafernandes.letsmovie.data.user.datasource.remote.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
) : AuthRepository {

    override suspend fun createGuestSession(): NetworkResult<Unit> {
        return if (isUserLoggedIn().not()) {
            safeApiCall {
                val guestSessionId = remoteDataSource.createGuestSession().guestSessionId
                localDataSource.saveUserSession(guestSessionId)
            }
        } else {
            NetworkResult.Success(Unit)
        }
    }

    override fun isUserLoggedIn(): Boolean = localDataSource.isLoggedIn()
}
