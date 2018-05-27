package com.leticiafernandes.letsmovie.presentation.view.mvpview

import android.content.Context
import android.support.v7.app.AppCompatActivity
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

    fun goToActivity(activityClass: Class<out AppCompatActivity>) {
        ActivityHelper.goToActivity(getContext(), activityClass)
    }

    fun getContext(): Context
}