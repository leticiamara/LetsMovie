package com.leticiafernandes.letsmovie.data.local

interface SessionManager {
    var guestSessionId: String?
    val isLoggedIn: Boolean
}
