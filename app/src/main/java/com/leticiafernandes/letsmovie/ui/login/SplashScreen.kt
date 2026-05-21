package com.leticiafernandes.letsmovie.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Primary
import com.leticiafernandes.letsmovie.ui.theme.Spacing

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_movie_white_48dp),
            contentDescription = null,
            modifier = Modifier.size(Dimens.splashLogoSize)
        )
        Spacer(modifier = Modifier.height(Spacing.medium))
        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
