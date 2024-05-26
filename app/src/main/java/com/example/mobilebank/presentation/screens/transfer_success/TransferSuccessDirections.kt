package com.example.mobilebank.presentation.screens.transfer_success

import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.main.MainScreen
import javax.inject.Inject

interface TransferSuccessDirections {
    suspend fun backMainScreen()
}

class TransferSuccessDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : TransferSuccessDirections {
    override suspend fun backMainScreen() =
        navigator.backUntil(MainScreen())

}