package com.example.mobilebank.presentation.screens.notification

import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.main.MainScreen
import javax.inject.Inject

interface NotificationDirections {
    suspend fun backMainScreen()
}

class NotificationDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : NotificationDirections {

    override suspend fun backMainScreen() =
        navigator.back()

}
