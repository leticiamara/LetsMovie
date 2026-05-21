package com.leticiafernandes.letsmovie.ui

import android.text.TextUtils
import android.util.Patterns

class EmailUtils {

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}