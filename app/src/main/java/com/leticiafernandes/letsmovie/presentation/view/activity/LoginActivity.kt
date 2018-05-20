
package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.infrastructure.data.SharedPreferencesManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.sign_in_sign_up_data.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "FIREBASE"
    private var firebaseAuth: FirebaseAuth? = null
    private var sharedPreferencesManager: SharedPreferencesManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferencesManager = SharedPreferencesManager(this)
        containerSignUp.setOnClickListener({
            goToActivity(SignUpActivity::class.java)
        })
        buttonSignInSignUp.setOnClickListener({
            signInUser()
        })
    }

    private fun signInUser() {
        firebaseAuth?.signInWithEmailAndPassword(editEmail.text.toString(),
                editPassword.text.toString())?.addOnCompleteListener({ task ->
            run {
                if (task.isSuccessful) {
                    signInUserSuccessful()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, task.exception?.message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        })
    }

    private fun signInUserSuccessful() {
        Log.d(TAG, "signInWithEmail:success")
        val user = firebaseAuth?.currentUser
        sharedPreferencesManager?.saveUserEmail(user?.email.toString())
        goToActivity(MoviesActivity::class.java)
    }

    private fun goToActivity(activityClass: Class<out AppCompatActivity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
