package com.leticiafernandes.letsmovie.presentation.presenter

import com.google.firebase.auth.FirebaseAuth
import com.leticiafernandes.letsmovie.infrastructure.SharedPreferencesManager
import com.leticiafernandes.letsmovie.presentation.view.activity.MoviesActivity
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignInMvpView

/**
 * Created by leticiafernandes on 27/05/18.
 */
class SignInPresenter(var mvpView: ISignInMvpView) : ISignInPresenter {

    private var firebaseAuth = FirebaseAuth.getInstance()
    private var sharedPreferencesManager = SharedPreferencesManager(mvpView.getContext())

    override fun signInUser(userEmail: String, userPassword: String) {
        firebaseAuth?.signInWithEmailAndPassword(userEmail, userPassword)?.addOnCompleteListener({ task ->
            run {
                if (task.isSuccessful) {
                    signInUserSuccessful()
                } else {
                    mvpView.showMessage(task.exception?.message!!)
                }
            }
        })
    }

    private fun signInUserSuccessful() {
        val user = firebaseAuth?.currentUser
        sharedPreferencesManager.saveUserEmail(user?.email.toString())
        mvpView.goToActivity(MoviesActivity::class.java, true)
    }
}