
package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun openLoginScreen() {
        loginPage.verifyLoginScreen()
    }

    fun login(username: String, password: String) {
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.clickLogin()
    }

    fun verifyPasswordError() {
        loginPage.verifyPasswordError()
    }

    fun verifyUsernameAndPasswordErrors() {
        loginPage.verifyUsernameError()
        loginPage.verifyPasswordError()
    }
    fun verifyHomeScreen() {
        loginPage.verifyHomeScreen()
    }
}
