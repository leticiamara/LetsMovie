package com.leticiafernandes.letsmovie.presentation.util

import android.text.TextUtils



/**
 * Created by leticiafernandes on 27/05/18.
 */
class EmailUtils {

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}