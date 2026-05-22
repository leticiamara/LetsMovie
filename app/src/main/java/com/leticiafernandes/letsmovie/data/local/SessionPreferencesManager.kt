package com.leticiafernandes.letsmovie.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionPreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var guestSessionId: String?
        get() = prefs.getString(KEY_GUEST_SESSION_ID, null)
        set(value) = prefs.edit().putString(KEY_GUEST_SESSION_ID, value).apply()

    val isLoggedIn: Boolean get() = guestSessionId != null

    companion object {
        private const val PREFS_NAME = "session_prefs"
        private const val KEY_GUEST_SESSION_ID = "guest_session_id"
    }
}
