package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChangePasswordPage {

    private val passwordField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun enterNewPassword(password: String) {
        onView(passwordField)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun enterRepeatedPassword(password: String) {
        onView(repeatPasswordField)
            .perform(replaceText(password), closeSoftKeyboard())
    }

    fun clickConfirm() {
        onView(withId(DesignSystemR.id.actionButton))
            .perform(click())
    }
}