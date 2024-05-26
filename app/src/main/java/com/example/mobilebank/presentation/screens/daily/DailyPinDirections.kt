package com.example.mobilebank.presentation.screens.daily

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.auth.AuthScreen
import com.example.mobilebank.presentation.screens.main.MainScreen
import javax.inject.Inject

interface DailyPinDirections {
    suspend fun navigateMainScreen()

    suspend fun navigateAuthScreen()
}

class DailyPinDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : DailyPinDirections {

    override suspend fun navigateMainScreen() =
        navigator.replaceScreen(MainScreen())

    override suspend fun navigateAuthScreen() =
        navigator.replaceScreen(AuthScreen())
}