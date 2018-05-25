package com.leticiafernandes.letsmovie.presentation.view

import android.app.Application

/**
 * Created by leticiafernandes on 24/05/18.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        private var INSTANCE: AppApplication? = null

        fun getInstance(): AppApplication? {
            return INSTANCE
        }
    }
}