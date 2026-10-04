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

class CreateChatPage {

    private val chatNameField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.chatNameInput))
    )

    fun enterChatName(chatName: String) {
        onView(chatNameField)
            .perform(replaceText(chatName), closeSoftKeyboard())
    }

    fun clickCreateChat() {
        onView(withId(R.id.createChatButton))
            .perform(click())
    }
    fun openCreateChat() {
        onView(withId(R.id.homeNavigationCreateChat))
            .perform(click())
    }
}