package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps {

    private val createChatPage = CreateChatPage()
    private val chatsPage = ChatsPage()

    fun openCreateChat() {
        createChatPage.openCreateChat()
    }

    fun createChat(chatName: String) {
        createChatPage.enterChatName(chatName)
        createChatPage.selectChatImage()
        createChatPage.clickCreateChat()
    }

    fun verifyCreatedChatIsOpened(chatName: String) {
        chatsPage.verifyOpenedChatName(chatName)
    }

    fun closeCreatedChat() {
        chatsPage.closeOpenedChat()
    }

    fun searchCreatedChat(chatName: String) {
        chatsPage.searchChat(chatName)
    }

    fun verifyCreatedChatIsDisplayed(chatName: String) {
        chatsPage.verifyChatIsDisplayed(chatName)
    }
}