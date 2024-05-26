package com.example.mobilebank.presentation.screens.sms

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.auth.AuthScreen
import com.example.mobilebank.presentation.screens.create.CreatePinScreen
import javax.inject.Inject

interface SmsDirections {
    suspend fun navigateCreatePinScreen()
    suspend fun backToAuthScreen()
}

class SmsDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : SmsDirections {

    override suspend fun navigateCreatePinScreen() =
        navigator.replaceScreen(CreatePinScreen())

    override suspend fun backToAuthScreen() =
        navigator.replaceScreen(AuthScreen())

}