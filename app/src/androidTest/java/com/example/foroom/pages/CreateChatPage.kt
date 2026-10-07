package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import com.example.design_system.components.image_chooser.ImageChooserListView
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

    fun selectChatImage() {
        onView(withId(R.id.chatImageChooser))
            .perform(object : ViewAction {

                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(ImageChooserListView::class.java)
                }

                override fun getDescription(): String {
                    return "Select second chat image"
                }

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {
                    val imageChooser = view as ImageChooserListView
                    imageChooser.selectImageAt(1)
                    uiController.loopMainThreadUntilIdle()
                }
            })
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