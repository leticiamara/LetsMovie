package com.leticiafernandes.letsmovie.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.ui.EmailUtils
import com.leticiafernandes.letsmovie.ui.theme.Accent
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Spacing
import com.leticiafernandes.letsmovie.ui.theme.TextDark

@Composable
fun SignUpScreen(
    onSignUpClick: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val emailUtils = EmailUtils()
    val errorInvalidEmail = stringResource(R.string.error_invalid_email)
    val errorPasswordTooShort = stringResource(R.string.error_password_too_short)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.titleLarge,
            color = TextDark,
            modifier = Modifier.padding(bottom = Spacing.extraLarge)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(Spacing.small))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = { Text(stringResource(id = R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null) },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(Spacing.small))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = { Text(stringResource(id = R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(Spacing.medium))

        Button(
            onClick = {
                var valid = true
                if (!emailUtils.isValidEmail(email)) {
                    emailError = errorInvalidEmail
                    valid = false
                }
                if (password.length < 6) {
                    passwordError = errorPasswordTooShort
                    valid = false
                }
                if (valid) {
                    onSignUpClick(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.buttonHeight),
            colors = ButtonDefaults.buttonColors(containerColor = Accent)
        ) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.sign_up),
                tint = Color.White,
                modifier = Modifier.size(Dimens.buttonIconSize)
            )
        }
    }
}
