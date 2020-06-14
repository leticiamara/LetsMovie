package com.leticiafernandes.letsmovie.presentation.view.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.utils.TextInputLayoutTestUtils
import org.junit.Rule
import org.junit.Test

/**
 * Created by leticiafernandes on 27/05/18.
 */
class LoginActivityTest {

    @get:Rule
    val testRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test fun showErrorWhenEmailIsInvalid() {
        onView(withId(R.id.editEmail)).perform(ViewActions.typeText("test.com"))
        onView(withId(R.id.editEmail)).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonSignInSignUp)).perform(click())
        onView(withId(R.id.textInputLayoutEmail)).check(matches(TextInputLayoutTestUtils
                .hasTextInputLayoutErrorText(testRule.activity.getString(R.string.error_invalid_email))))
    }

    @Test fun showErrorWhenPasswordIsLessThanSix() {
        onView(withId(R.id.editPassword)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.editPassword)).perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.buttonSignInSignUp)).perform(click())
        onView(withId(R.id.textInputLayoutPassword)).check(matches(TextInputLayoutTestUtils
                .hasTextInputLayoutErrorText(testRule.activity.getString(R.string.error_password_less_than_six))))
    }
}