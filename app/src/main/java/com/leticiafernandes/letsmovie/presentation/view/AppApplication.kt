package com.leticiafernandes.letsmovie.presentation.view

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
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