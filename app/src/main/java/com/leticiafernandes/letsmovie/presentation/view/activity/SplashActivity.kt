package com.leticiafernandes.letsmovie.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.SharedPreferencesManager

class SplashActivity : AppCompatActivity() {

    private var sharedPreferencesManager: SharedPreferencesManager? = null
    private val handler: Handler = Handler()

    private val DELAY_TIME = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferencesManager = SharedPreferencesManager(this)

        handler.postDelayed({
            if (!sharedPreferencesManager?.getUserEmail()?.isEmpty()!!) {
                gotToActivity(MainActivity::class.java)
            } else {
                gotToActivity(LoginActivity::class.java)
            }
        }, DELAY_TIME)
    }

    private fun gotToActivity(activityClass: Class<out AppCompatActivity>) {
        val intent = Intent(this, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
