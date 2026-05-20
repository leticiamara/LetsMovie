package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.databinding.ActivitySignUpBinding
import com.leticiafernandes.letsmovie.presentation.presenter.ISignUpPresenter
import com.leticiafernandes.letsmovie.presentation.presenter.SignUpPresenter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.ISignUpMvpView

class SignUpActivity : AppCompatActivity(), ISignUpMvpView {

    private lateinit var binding: ActivitySignUpBinding
    lateinit var presenter: ISignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SignUpPresenter(this)
        binding.signInSignUpData.signInSignUpTitle.setText(R.string.sign_up)
        binding.signInSignUpData.buttonSignInSignUp.setOnClickListener {
            if (!binding.signInSignUpData.editEmail.text.isEmpty() &&
                !binding.signInSignUpData.editPassword.text.isEmpty()) {
                presenter.signUpUser(
                    binding.signInSignUpData.editEmail.text.toString(),
                    binding.signInSignUpData.editPassword.text.toString()
                )
            }
        }
    }

    override fun getContext(): Context {
        return this
    }
}
