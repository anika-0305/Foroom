
package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import java.util.concurrent.TimeoutException

class LoginPage {

    private val usernameField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordField = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val usernameError = allOf(
        withId(DesignSystemR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordError = allOf(
        withId(DesignSystemR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    fun verifyLoginScreen() {
        onView(withId(R.id.logInButton))
            .check(matches(isDisplayed()))
    }

    fun enterUsername(username: String) {
        onView(usernameField)
            .perform(
                replaceText(username),
                closeSoftKeyboard()
            )
    }

    fun enterPassword(password: String) {
        onView(passwordField)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun clickLogin() {
        onView(withId(R.id.logInButton))
            .perform(click())
    }

    fun clickSignUp() {
        onView(withId(R.id.signUpButton))
            .perform(click())
    }

    fun verifyUsernameError() {
        waitForError(usernameError)
    }

    fun verifyPasswordError() {
        waitForError(passwordError)
    }

    private fun waitForError(
        matcher: Matcher<View>,
        timeoutMs: Long = 15_000
    ) {
        val instrumentation =
            InstrumentationRegistry.getInstrumentation()

        val errorMatcher = allOf(
            matcher,
            isDisplayed(),
            withText(org.hamcrest.Matchers.not("")),
            hasTextColor(
                DesignSystemR.color.foroom_background_pink
            )
        )

        val deadline =
            System.currentTimeMillis() + timeoutMs

        while (System.currentTimeMillis() < deadline) {
            try {
                onView(errorMatcher)
                    .check(matches(isDisplayed()))

                return

            } catch (_: NoMatchingViewException) {
                // Error has not appeared yet.
            } catch (_: AssertionError) {
                // Error is not ready yet.
            }

            instrumentation.waitForIdleSync()
            Thread.sleep(250)
        }

        throw TimeoutException(
            "Expected login error did not appear within $timeoutMs ms"
        )
    }
    fun verifyHomeScreen() {
        onView(withId(R.id.navBar))
            .check(matches(isDisplayed()))
    }
}
