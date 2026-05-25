package com.leticiafernandes.letsmovie.ui.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsAppName() {
        composeTestRule.setContent {
            SplashScreen()
        }

        composeTestRule.onNodeWithText("Lets Movie").assertIsDisplayed()
    }

    @Test
    fun showsTagline() {
        composeTestRule.setContent {
            SplashScreen()
        }

        composeTestRule.onNodeWithText("Discover your next favorite film").assertIsDisplayed()
    }
}
