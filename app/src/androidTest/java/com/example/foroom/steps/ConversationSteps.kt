package com.example.foroom.steps

import com.example.foroom.Helper.swiper
import com.example.foroom.pages.ConversationPage

class ConversationSteps {

    private val conversationPage = ConversationPage()

    fun verifyConversationIsOpen(chatName: String): ConversationSteps {
        conversationPage.verifyConversationIsOpen(chatName)
        return this
    }

    fun sendMessage(message: String): ConversationSteps {
        conversationPage.sendMessage(message)
        return this
    }

    fun verifyMessageIsDisplayed(message: String): ConversationSteps {
        conversationPage.verifyMessageIsDisplayed(message)
        return this
    }

    fun sendAndVerifyMessage(message: String): ConversationSteps {
        conversationPage.sendMessage(message)
        conversationPage.verifyMessageIsDisplayed(message)
        return this
    }

    fun verifyMessageWithSender(
        message: String,
        sender: String
    ): ConversationSteps {
        conversationPage.verifyMessageWithSender(
            message = message,
            sender = sender
        )
        return this
    }

    fun closeConversation(): ConversationSteps {
        conversationPage.closeConversation()
        return this
    }

    fun sendAdditionalMessages(
        count: Int,
        messagePrefix: String = "additional message"
    ): ConversationSteps {
        repeat(count) { index ->
            val message = "$messagePrefix ${index + 1}"

            conversationPage.sendMessage(message)
            conversationPage.verifyMessageIsDisplayed(message)
        }

        return this
    }

    fun findOlderMessage(
        message: String,
        maxSwipeAttempts: Int = 10
    ): ConversationSteps {
        repeat(maxSwipeAttempts) {
            if (isMessageVisible(message)) {
                conversationPage.verifyMessageIsDisplayed(message)
                return this
            }

            swipeTowardsOlderMessages()
        }

        throw AssertionError(
            "Message '$message' was not found after $maxSwipeAttempts swipe attempts"
        )
    }

    fun findOlderMessageWithSender(
        message: String,
        sender: String,
        maxSwipeAttempts: Int = 10
    ): ConversationSteps {
        repeat(maxSwipeAttempts) {
            if (isMessageVisible(message)) {
                conversationPage.verifyMessageWithSender(
                    message = message,
                    sender = sender
                )
                return this
            }

            swipeTowardsOlderMessages()
        }

        throw AssertionError(
            "Message '$message' from sender '$sender' " +
                    "was not found after $maxSwipeAttempts swipe attempts"
        )
    }

    private fun isMessageVisible(message: String): Boolean {
        return try {
            conversationPage.verifyMessageIsDisplayed(message)
            true
        } catch (_: Throwable) {
            false
        }
    }

    private fun swipeTowardsOlderMessages() {
        val (start, end) =
            conversationPage.getOlderMessagesSwipeCoordinates()

        swiper(
            start = start,
            end = end,
            delay = 300
        )
    }
}