package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.presentation.presenter.ISignUpPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.SignUpPresenter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignUpMvpView
import kotlinx.android.synthetic.main.sign_in_sign_up_data.*

class SignUpActivity : AppCompatActivity(), ISignUpMvpView {

    lateinit var presenter: ISignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        presenter = SignUpPresenter(this)
        signInSignUpTitle.setText(R.string.sign_up)
        buttonSignInSignUp.setOnClickListener({
            if (!editEmail.text.isEmpty() && !editPassword.text.isEmpty()) {
                presenter.signUpUser(editEmail.text.toString(), editPassword.text.toString())
            }
        })
    }

    override fun getContext(): Context {
        return this
    }
}
