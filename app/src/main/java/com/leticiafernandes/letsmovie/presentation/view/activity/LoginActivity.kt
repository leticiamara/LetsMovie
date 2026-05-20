package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leticiafernandes.letsmovie.databinding.ActivityLoginBinding
import com.leticiafernandes.letsmovie.presentation.helper.ActivityHelper.Companion.goToActivity
import com.leticiafernandes.letsmovie.presentation.presenter.ISignInPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.SignInPresenter
import com.leticiafernandes.letsmovie.presentation.util.EmailUtils
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignInMvpView
import com.leticiafernandes.letsmovie.R

class LoginActivity : AppCompatActivity(), ISignInMvpView {

    private lateinit var binding: ActivityLoginBinding
    lateinit var presenter: ISignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SignInPresenter(this)
        binding.containerSignUp.setOnClickListener {
            goToActivity(this, SignUpActivity::class.java)
        }
        binding.signInSignUpData.buttonSignInSignUp.setOnClickListener {
            if (validateFields()) {
                (presenter as SignInPresenter).signInUser(
                    binding.signInSignUpData.editEmail.text.toString(),
                    binding.signInSignUpData.editPassword.text.toString()
                )
            }
        }
    }

    override fun getContext(): Context {
        return this
    }

    private fun validateFields(): Boolean {
        var valid = true
        if (!EmailUtils().isValidEmail(binding.signInSignUpData.editEmail.text)) {
            binding.signInSignUpData.textInputLayoutEmail.error = getString(R.string.error_invalid_email)
            valid = false
        } else {
            binding.signInSignUpData.textInputLayoutEmail.error = null
        }
        if (binding.signInSignUpData.editPassword.text.length < 6) {
            binding.signInSignUpData.textInputLayoutPassword.error = getString(R.string.error_password_less_than_six)
            valid = false
        } else {
            binding.signInSignUpData.textInputLayoutPassword.error = null
        }
        return valid
    }
}
