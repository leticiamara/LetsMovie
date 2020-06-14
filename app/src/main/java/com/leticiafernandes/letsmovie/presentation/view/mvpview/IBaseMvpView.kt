package com.leticiafernandes.letsmovie.presentation.view.mvpview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.leticiafernandes.letsmovie.presentation.helper.ActivityHelper

/**
 * Created by leticiafernandes on 27/05/18.
 */
interface IBaseMvpView {

    fun showMessage(resource: Int) {
        Toast.makeText(getContext(), getContext().getString(resource), Toast.LENGTH_SHORT).show()
    }

    fun showMessage(message: String) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun goToActivity(activityClass: Class<out AppCompatActivity>, clearTask: Boolean = false) {
        ActivityHelper.goToActivity(getContext(), activityClass, clearTask)
    }

    fun getContext(): Context
}