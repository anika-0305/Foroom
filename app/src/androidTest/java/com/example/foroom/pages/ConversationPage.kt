package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ConversationPage {

    private val messageInputField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.messageInput))
    )

    fun verifyConversationIsOpen(chatName: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.chatNameTextView),
                withText(chatName),
                isDisplayed()
            )
        ).check(matches(isDisplayed()))
    }

    fun enterMessage(message: String) {
        onView(messageInputField)
            .perform(
                click(),
                replaceText(message),
                closeSoftKeyboard()
            )
    }

    fun tapSendMessage() {
        onView(
            allOf(
                withId(R.id.sendMessageButton),
                isDisplayed()
            )
        ).perform(click())
    }

    fun sendMessage(message: String) {
        enterMessage(message)
        tapSendMessage()
    }

    fun verifyMessageIsDisplayed(message: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.messageTextView),
                withText(message)
            )
        ).check(matches(withText(message)))
    }

    fun verifyMessageWithSender(
        message: String,
        sender: String
    ) {
        onView(
            allOf(
                withId(R.id.messageView),
                hasDescendant(
                    allOf(
                        withId(DesignSystemR.id.messageTextView),
                        withText(message)
                    )
                ),
                hasDescendant(
                    allOf(
                        withId(DesignSystemR.id.userNameTextView),
                        withText(sender)
                    )
                )
            )
        ).check(matches(hasDescendant(withText(message))))
    }

    fun getOlderMessagesSwipeCoordinates(): Pair<Int, Int> {
        var start = 0
        var end = 0

        onView(
            allOf(
                withId(R.id.messagesRecyclerView),
                isDisplayed()
            )
        ).perform(
            object : ViewAction {

                override fun getConstraints(): Matcher<View> =
                    isDisplayed()

                override fun getDescription(): String =
                    "Get device-aware swipe coordinates from messages RecyclerView"

                override fun perform(
                    uiController: UiController,
                    view: View
                ) {
                    val location = IntArray(2)
                    view.getLocationOnScreen(location)

                    val top = location[1]
                    val height = view.height

                    start = (top + height * 0.30f).toInt()
                    end = (top + height * 0.75f).toInt()

                    uiController.loopMainThreadUntilIdle()
                }
            }
        )

        return start to end
    }

    fun closeConversation() {
        onView(
            allOf(
                withId(R.id.closeButton),
                isDisplayed()
            )
        ).perform(click())
    }
}