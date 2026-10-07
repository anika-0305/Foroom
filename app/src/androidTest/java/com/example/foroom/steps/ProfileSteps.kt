package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {

    private val profilePage = ProfilePage()
    private val changePasswordPage = ChangePasswordPage()
    private val changeLanguagePage = ChangeLanguagePage()

    fun openProfile() {
        profilePage.openProfile()
        profilePage.verifyProfileScreen()
    }

    fun openChangePassword() {
        profilePage.clickChangePassword()
    }

    fun changePassword(newPassword: String) {
        changePasswordPage.enterNewPassword(newPassword)
        changePasswordPage.enterRepeatedPassword(newPassword)
        changePasswordPage.clickConfirm()
    }

    fun openChangeLanguage() {
        profilePage.clickChangeLanguage()
    }

    fun selectGeorgianAndVerify() {
        changeLanguagePage.selectGeorgian()
        profilePage.verifyGeorgianLanguage()
    }

    fun selectEnglishAndVerify() {
        changeLanguagePage.selectEnglish()
        profilePage.verifyEnglishLanguage()
    }
    fun signOut() {
        profilePage.clickSignOut()
    }
}