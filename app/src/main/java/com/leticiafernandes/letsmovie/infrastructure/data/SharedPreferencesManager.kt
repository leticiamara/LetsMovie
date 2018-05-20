package com.leticiafernandes.letsmovie.infrastructure.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by leticiafernandes on 20/05/18.
 */
class SharedPreferencesManager(context: Context) {

    private var sharedPreferences: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
    private val USER_EMAIL_KEY = "user-email"

    fun saveUserEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_EMAIL_KEY, email)
        editor.apply()
    }

    fun getUserEmail() : String {
        return sharedPreferences.getString(USER_EMAIL_KEY, "")
    }
}