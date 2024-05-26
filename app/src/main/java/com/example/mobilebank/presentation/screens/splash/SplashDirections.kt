package com.example.mobilebank.presentation.screens.splash

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.auth.AuthScreen
import com.example.mobilebank.presentation.screens.daily.DailyPinScreen
import javax.inject.Inject

interface SplashDirections {
    suspend fun navigateAuthScreen()
    suspend fun navigatePinScreen()
}

class SplashDirectionsImp @Inject constructor(
    val navigator: AppNavigator
) : SplashDirections {
    override suspend fun navigateAuthScreen() {
        navigator.replaceScreen(AuthScreen())
    }

    override suspend fun navigatePinScreen() {
        navigator.replaceScreen(DailyPinScreen())
    }
}