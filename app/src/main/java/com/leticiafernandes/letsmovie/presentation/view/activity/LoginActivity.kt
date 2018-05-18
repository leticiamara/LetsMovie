
package com.leticiafernandes.letsmovie.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leticiafernandes.letsmovie.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        containerSignUp.setOnClickListener({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })
    }
}
