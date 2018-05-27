
package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.presentation.helper.ActivityHelper.Companion.goToActivity
import com.leticiafernandes.letsmovie.presentation.presenter.ISignInPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.SignInPresenter
import com.leticiafernandes.letsmovie.presentation.util.EmailUtils
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignInMvpView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sign_in_sign_up_data.*

class LoginActivity : AppCompatActivity(), ISignInMvpView {

    lateinit var presenter: ISignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = SignInPresenter(this)
        containerSignUp.setOnClickListener({
            goToActivity(this, SignUpActivity::class.java)
        })
        buttonSignInSignUp.setOnClickListener({
             if (validateFields()) {
                (presenter as SignInPresenter).signInUser(editEmail.text.toString(),
                        editPassword.text.toString())
            }
        })
    }

    override fun getContext(): Context {
        return this
    }

    private fun validateFields() : Boolean {
        var valid = true
        if (!EmailUtils().isValidEmail(editEmail.text)) {
            textInputLayoutEmail.error = getString(R.string.error_invalid_email)
            valid = false
        } else {
            textInputLayoutEmail.error = null
        }
        if(editPassword.text.length < 6) {
            textInputLayoutPassword.error = getString(R.string.error_password_less_than_six)
            valid = false
        } else {
            textInputLayoutPassword.error = null
        }
        return valid
    }
}
