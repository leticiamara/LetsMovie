package com.leticiafernandes.letsmovie.ui.login

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leticiafernandes.letsmovie.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context get() = ApplicationProvider.getApplicationContext()

    @Test
    fun showsAppName() {
        composeTestRule.setContent {
            SplashScreen()
        }

        composeTestRule.onNodeWithText(context.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    fun showsTagline() {
        composeTestRule.setContent {
            SplashScreen()
        }

        composeTestRule.onNodeWithText(context.getString(R.string.splash_tagline)).assertIsDisplayed()
    }
}
