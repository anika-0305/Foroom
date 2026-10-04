package com.example.foroom

import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object TestSessionHelper : KoinComponent {

    private val userDataStore: ForoomUserDataStore by inject()

    fun clearSession() {
        runBlocking {
            userDataStore.clearUserData()
        }
    }
}
