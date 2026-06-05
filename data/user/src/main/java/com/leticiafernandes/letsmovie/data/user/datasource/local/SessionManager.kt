package com.leticiafernandes.letsmovie.data.user.datasource.local

interface SessionManager {
    var guestSessionId: String?
    val isLoggedIn: Boolean
}
