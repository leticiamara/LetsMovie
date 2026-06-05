package com.leticiafernandes.letsmovie.data.user.datasource.local

import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(val sessionManager: SessionManager) : AuthLocalDataSource {

    override fun isLoggedIn(): Boolean = sessionManager.isLoggedIn

    override fun saveUserSession(sessionId: String) {
        sessionManager.guestSessionId = sessionId
    }

    override fun getGuestSessionId(): String? = sessionManager.guestSessionId
}
