package com.leticiafernandes.letsmovie.fake

import com.leticiafernandes.letsmovie.data.local.SessionManager

class FakeSessionManager : SessionManager {
    override var guestSessionId: String? = null
    override val isLoggedIn: Boolean get() = guestSessionId != null
}
