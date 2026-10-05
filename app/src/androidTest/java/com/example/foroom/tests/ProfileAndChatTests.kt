package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.TestSessionHelper
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    private val clearSessionRule = object : ExternalResource() {
        override fun before() {
            TestSessionHelper.clearSession()
        }
    }

    private val activityRule =
        ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(clearSessionRule)
        .around(activityRule)
    @Test
    fun changePasswordAndLoginWithNewPassword() {

        val username = "Anika"
        val currentPassword = "Aa123123@"
        val newPassword = "Bb123123@"

        loginSteps.openLoginScreen()
        loginSteps.login(username, currentPassword)
        loginSteps.verifyHomeScreen()

        profileSteps.openProfile()
        profileSteps.openChangePassword()
        profileSteps.changePassword(newPassword)

        loginSteps.openLoginScreen()

        loginSteps.login(username, newPassword)
        loginSteps.verifyHomeScreen()

        // Restore the original password for independent test runs
        profileSteps.openProfile()
        profileSteps.openChangePassword()
        profileSteps.changePassword(currentPassword)

        loginSteps.openLoginScreen()
    }
    @Test
    fun changeLanguageFromGeorgianToEnglishAndBack() {

        val username = "Anika"
        val password = "Aa123123@"

        loginSteps.openLoginScreen()
        loginSteps.login(username, password)
        loginSteps.verifyHomeScreen()

        profileSteps.openProfile()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgianAndVerify()

        profileSteps.openChangeLanguage()
        profileSteps.selectEnglishAndVerify()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgianAndVerify()
    }
    @Test
    fun createChatAndFindItInChatsList() {

        val username = "Anika"
        val password = "Aa123123@"
        val chatName = "Anika_${System.currentTimeMillis()}"

        loginSteps.openLoginScreen()
        loginSteps.login(username, password)
        loginSteps.verifyHomeScreen()

        chatSteps.openCreateChat()
        chatSteps.createChat(chatName)
        chatSteps.verifyCreatedChatIsOpened(chatName)

        chatSteps.closeCreatedChat()
        chatSteps.searchCreatedChat(chatName)
        chatSteps.verifyCreatedChatIsDisplayed(chatName)
    }
}