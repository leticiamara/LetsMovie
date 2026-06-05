package com.leticiafernandes.letsmovie.data.user.datasource.local

interface AuthLocalDataSource {

    fun isLoggedIn(): Boolean

    fun saveUserSession(sessionId: String)

    fun getGuestSessionId(): String?
}
