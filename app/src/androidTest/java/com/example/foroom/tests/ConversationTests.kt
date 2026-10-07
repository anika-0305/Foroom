package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foroom.TestSessionHelper
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversationTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private lateinit var loginSteps: LoginSteps
    private lateinit var chatSteps: ChatSteps
    private lateinit var conversationSteps: ConversationSteps
    private lateinit var profileSteps: ProfileSteps

    private val arguments
        get() = InstrumentationRegistry.getArguments()

    private val userAUsername
        get() = requireNotNull(
            arguments.getString("USER_A_USERNAME")
        ) {
            "USER_A_USERNAME is required"
        }

    private val userAPassword
        get() = requireNotNull(
            arguments.getString("USER_A_PASSWORD")
        ) {
            "USER_A_PASSWORD is required"
        }

    private val userBUsername
        get() = requireNotNull(
            arguments.getString("USER_B_USERNAME")
        ) {
            "USER_B_USERNAME is required"
        }

    private val userBPassword
        get() = requireNotNull(
            arguments.getString("USER_B_PASSWORD")
        ) {
            "USER_B_PASSWORD is required"
        }

    private val userAFullName: String
        get() = arguments.getString("USER_A_FULL_NAME")
            ?.takeIf { it.isNotBlank() }
            ?: "Anika"

    private val johnWeekChat = "johnWeek"
    private val fullNameChat = "Anika_Automation"
    private val sharedChat = "something"

    @Before
    fun setUp() {
        TestSessionHelper.clearSession()

        activityRule.scenario.onActivity { activity ->
            activity.recreate()
        }

        loginSteps = LoginSteps()
        chatSteps = ChatSteps()
        conversationSteps = ConversationSteps()
        profileSteps = ProfileSteps()
    }

    @Test
    fun userASendsMessageAndMessagePersistsAfterReopeningChat() {

        val message =
            "let's go for a drink ${System.currentTimeMillis()}"

        loginAsUserA()

        openChat(johnWeekChat)

        conversationSteps
            .verifyConversationIsOpen(johnWeekChat)
            .sendAndVerifyMessage(message)
            .closeConversation()

        openChat(johnWeekChat)

        conversationSteps
            .verifyConversationIsOpen(johnWeekChat)
            .verifyMessageIsDisplayed(message)
    }

    @Test
    fun userASendsAutomationAcademyQuestionInFullNameChat() {

        val uniqueSuffix = System.currentTimeMillis()

        val question =
            "Which Automation Academy module do you like most? $uniqueSuffix"

        loginAsUserA()

        openChat(fullNameChat)

        conversationSteps
            .verifyConversationIsOpen(fullNameChat)
            .sendAndVerifyMessage(question)
    }

    @Test
    fun usersExchangeMessagesAndOlderGreetingRemainsVisible() {

        val uniqueSuffix = System.currentTimeMillis()

        val greeting =
            "Hello from User A $uniqueSuffix"

        val reply =
            "Hello from User B $uniqueSuffix"

        // User A logs in
        loginAsUserA()

        // User A opens shared chat
        openChat(sharedChat)

        // User A sends greeting and 25 additional messages
        conversationSteps
            .verifyConversationIsOpen(sharedChat)
            .sendAndVerifyMessage(greeting)
            .sendAdditionalMessages(
                count = 25,
                messagePrefix = "history message $uniqueSuffix"
            )
            .closeConversation()

        // User A signs out
        signOut()

        // User B logs in
        loginAsUserB()

        // User B opens the same shared chat
        openChat(sharedChat)

        // User B uses swiper to find User A's older greeting
        // and verifies both message and sender
        conversationSteps
            .verifyConversationIsOpen(sharedChat)
            .findOlderMessageWithSender(
                message = greeting,
                sender = userAFullName,
                maxSwipeAttempts = 10
            )
            .sendAndVerifyMessage(reply)
            .closeConversation()

        // User B signs out
        signOut()

        // User A logs back in
        loginAsUserA()

        // User A opens the shared chat again
        openChat(sharedChat)

        // Verify shared conversation opens successfully
        conversationSteps
            .verifyConversationIsOpen(sharedChat)
    }

    private fun loginAsUserA() {
        loginSteps.openLoginScreen()

        loginSteps.login(
            username = userAUsername,
            password = userAPassword
        )

        loginSteps.verifyHomeScreen()
    }

    private fun loginAsUserB() {
        loginSteps.openLoginScreen()

        loginSteps.login(
            username = userBUsername,
            password = userBPassword
        )

        loginSteps.verifyHomeScreen()
    }

    private fun openChat(chatName: String) {
        chatSteps.searchCreatedChat(chatName)
        chatSteps.openChat(chatName)
    }

    private fun signOut() {
        profileSteps.openProfile()
        profileSteps.signOut()
    }
}