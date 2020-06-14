package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.SharedPreferencesManager

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
                gotToActivity(MoviesActivity::class.java)
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
