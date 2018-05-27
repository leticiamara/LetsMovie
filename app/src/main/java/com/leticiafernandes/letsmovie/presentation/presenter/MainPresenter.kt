package com.leticiafernandes.letsmovie.presentation.presenter

import com.google.firebase.auth.FirebaseAuth
import com.leticiafernandes.letsmovie.infrastructure.SharedPreferencesManager
import com.leticiafernandes.letsmovie.presentation.view.activity.LoginActivity
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IMainMvpView

/**
 * Created by leticiafernandes on 27/05/18.
 */
class MainPresenter(var mvpView: IMainMvpView) : IMainPresenter {

    private var firebaseAuth = FirebaseAuth.getInstance()
    private var sharedPreferencesManager = SharedPreferencesManager(mvpView.getContext())

    override fun logout() {
        firebaseAuth?.currentUser?.uid
        firebaseAuth?.signOut()
        sharedPreferencesManager.saveUserEmail("")
        mvpView.goToActivity(LoginActivity::class.java)
    }
}