package com.leticiafernandes.letsmovie.utils

import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import com.google.android.material.textfield.TextInputLayout



/**
 * Created by leticiafernandes on 27/05/18.
 */
class TextInputLayoutTestUtils {

    companion object {

        fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View> {
            val typeSafeMatcher = object: TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {
                }

                override fun matchesSafely(item: View?): Boolean {
                    if (item !is TextInputLayout) {
                        return false
                    }

                    val error = item.error ?: return false
                    val hint = error.toString()
                    return expectedErrorText.equals(hint)
                }
            }
            return typeSafeMatcher
        }
    }
}