package com.example.mobilebank.presentation.screens.auth

import com.example.mobilebank.data.model.SmsRequestType
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.sms.SmsScreen
import javax.inject.Inject

interface AuthDirections {
    suspend fun navigateSmsScreen(data: SmsRequestType)
}

class AuthDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : AuthDirections {

    override suspend fun navigateSmsScreen(data: SmsRequestType) =
        navigator.replaceScreen(SmsScreen(data))

}