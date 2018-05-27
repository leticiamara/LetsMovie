package com.leticiafernandes.letsmovie.presentation.helper

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by leticiafernandes on 27/05/18.
 */
class ActivityHelper {

    companion object {

        fun goToActivity(context: Context, activityClass: Class<out AppCompatActivity>) {
            val intent = Intent(context, activityClass)
            context.startActivity(intent)
        }
    }
}