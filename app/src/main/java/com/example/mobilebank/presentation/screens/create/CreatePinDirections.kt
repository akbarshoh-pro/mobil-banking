package com.example.mobilebank.presentation.screens.create

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.confirm.ConfirmPinScreen
import javax.inject.Inject

interface CreatePinDirections {
    suspend fun navigateConfirmPinScreen()
}

class CreatePinDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : CreatePinDirections {
    override suspend fun navigateConfirmPinScreen() {
        navigator.replaceScreen(ConfirmPinScreen())
    }

}