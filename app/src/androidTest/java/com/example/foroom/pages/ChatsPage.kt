package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignSystemR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import androidx.test.espresso.NoMatchingViewException
import androidx.test.platform.app.InstrumentationRegistry
import java.util.concurrent.TimeoutException
class ChatsPage {

    private val searchChatField: Matcher<View> = allOf(
        withId(DesignSystemR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    fun searchChat(chatName: String) {
        onView(searchChatField)
            .perform(replaceText(chatName), closeSoftKeyboard())
    }

    fun verifyOpenedChatName(chatName: String) {

        val openedChatTitle = allOf(
            withId(DesignSystemR.id.chatNameTextView),
            withText(chatName)
        )

        waitForView(openedChatTitle)
    }

    fun closeOpenedChat() {
        onView(withId(R.id.closeButton))
            .perform(click())
    }

    fun verifyChatIsDisplayed(chatName: String) {
        onView(
            allOf(
                withId(DesignSystemR.id.chatTitleTextView),
                withText(chatName),
                isDescendantOfA(withId(R.id.chatsRecyclerView))
            )
        ).check(matches(isDisplayed()))
    }
    private fun waitForView(
        matcher: Matcher<View>,
        timeoutMs: Long = 10_000
    ) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val deadline = System.currentTimeMillis() + timeoutMs

        while (System.currentTimeMillis() < deadline) {
            try {
                onView(matcher).check(matches(isDisplayed()))
                return
            } catch (_: NoMatchingViewException) {
            } catch (_: AssertionError) {
            }

            instrumentation.waitForIdleSync()
            Thread.sleep(250)
        }

        throw TimeoutException(
            "Expected view was not displayed within $timeoutMs ms"
        )
    }
}