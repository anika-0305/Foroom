package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import java.util.concurrent.TimeoutException

class ChatsPage {

    private val searchChatField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun searchChat(chatName: String) {
        onView(searchChatField)
            .perform(
                replaceText(chatName),
                closeSoftKeyboard()
            )
    }

    fun openChat(chatName: String) {

        val chatItemMatcher = hasDescendant(
            allOf(
                withId(DesignSystemR.id.chatTitleTextView),
                withText(chatName)
            )
        )

        onView(
            allOf(
                withId(R.id.chatsRecyclerView),
                isDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                chatItemMatcher,
                clickChildViewWithId(DesignSystemR.id.sendMessageButton)
            )
        )
    }

    fun verifyOpenedChatName(chatName: String) {

        val openedChatTitle = allOf(
            withId(DesignSystemR.id.chatNameTextView),
            withText(chatName),
            isDisplayed()
        )

        waitForView(openedChatTitle)
    }

    fun closeOpenedChat() {
        onView(
            allOf(
                withId(R.id.closeButton),
                isDisplayed()
            )
        ).perform(click())
    }

    private fun clickChildViewWithId(id: Int): ViewAction {

        return object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Click child view with id: $id"
            }

            override fun perform(
                uiController: androidx.test.espresso.UiController,
                view: View
            ) {
                val childView = view.findViewById<View>(id)

                if (childView == null) {
                    throw AssertionError(
                        "Child view with id $id was not found inside chat item"
                    )
                }

                childView.performClick()
                uiController.loopMainThreadUntilIdle()
            }
        }
    }

    private fun waitForView(
        matcher: Matcher<View>,
        timeoutMs: Long = 10_000
    ) {

        val instrumentation =
            InstrumentationRegistry.getInstrumentation()

        val deadline =
            System.currentTimeMillis() + timeoutMs

        while (System.currentTimeMillis() < deadline) {

            try {
                onView(matcher)
                    .check(matches(isDisplayed()))

                return

            } catch (_: NoMatchingViewException) {
                // View is not available yet.

            } catch (_: AssertionError) {
                // View exists but is not ready yet.
            }

            instrumentation.waitForIdleSync()
            Thread.sleep(250)
        }

        throw TimeoutException(
            "Expected view was not displayed within $timeoutMs ms"
        )
    }
}