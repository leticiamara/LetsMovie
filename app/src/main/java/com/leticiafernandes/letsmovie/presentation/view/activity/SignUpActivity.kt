package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.leticiafernandes.letsmovie.R
import kotlinx.android.synthetic.main.sign_in_sign_up_data.*

class SignUpActivity : AppCompatActivity() {

    private val TAG = "FIREBASE"
    var firebaseAuth: FirebaseAuth? = null

    override fun onStart() {
        super.onStart()
        var currentUser: FirebaseUser? = firebaseAuth?.currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()
        signInSignUpTitle.setText(R.string.sign_up)
        buttonSignInSignUp.setOnClickListener({
            signUpUser()
        })
    }

    private fun signUpUser() {
        if (firebaseAuth != null) {
            firebaseAuth!!.createUserWithEmailAndPassword(editEmail.text.toString(),
                    editPassword.text.toString())
                    .addOnCompleteListener({ task ->
                        run {
                            if (task.isSuccessful) {
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = firebaseAuth?.currentUser
                                val intent = Intent(this, MoviesActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception);
                                Toast.makeText(this, task.exception?.message,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
        }
    }
}
