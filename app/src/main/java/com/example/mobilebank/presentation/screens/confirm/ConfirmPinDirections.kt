package com.example.mobilebank.presentation.screens.confirm

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.main.MainScreen
import javax.inject.Inject

interface ConfirmPinDirections {
    suspend fun navigateMainScreen()
}

class ConfirmPinDirectionsImp @Inject constructor(
    val navigator: AppNavigator
) : ConfirmPinDirections {
    override suspend fun navigateMainScreen() {
        navigator.replaceScreen(MainScreen())
    }

}