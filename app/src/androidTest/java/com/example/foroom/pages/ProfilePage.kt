package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matchers.allOf

class ProfilePage {

    fun openProfile() {
        onView(withId(R.id.homeNavigationProfile))
            .perform(click())
    }

    fun clickChangePassword() {
        onView(withId(R.id.changePasswordItem))
            .perform(click())
    }

    fun clickChangeLanguage() {
        onView(withId(R.id.changeLanguageItem))
            .perform(click())
    }

    fun verifyProfileScreen() {
        onView(withId(R.id.changePasswordItem))
            .check(matches(isDisplayed()))
    }

    fun verifyGeorgianLanguage() {
        onView(
            allOf(
                withText("ენის შეცვლა"),
                isDescendantOfA(withId(R.id.changeLanguageItem))
            )
        ).check(matches(isDisplayed()))
    }

    fun verifyEnglishLanguage() {
        onView(
            allOf(
                withText("Change Language"),
                isDescendantOfA(withId(R.id.changeLanguageItem))
            )
        ).check(matches(isDisplayed()))
    }
}