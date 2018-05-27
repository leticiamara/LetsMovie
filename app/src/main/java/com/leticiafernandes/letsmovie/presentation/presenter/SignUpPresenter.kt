package com.leticiafernandes.letsmovie.presentation.presenter

import com.google.firebase.auth.FirebaseAuth
import com.leticiafernandes.letsmovie.presentation.view.activity.MoviesActivity
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignUpMvpView

/**
 * Created by leticiafernandes on 27/05/18.
 */
class SignUpPresenter(var mvpView: ISignUpMvpView) : ISignUpPresenter {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun signUpUser(userEmail: String, userPassword: String) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener({ task ->
                    run {
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            mvpView.goToActivity(MoviesActivity::class.java)
                        } else {
                            mvpView.showMessage(task.exception?.message!!)
                        }
                    }
                })

    }

}