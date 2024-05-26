package com.example.mobilebank.presentation.screens.transfer_card

import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.add_card.AddCardScreen
import com.example.mobilebank.presentation.screens.transfer_verify.TransferVerifyScreen
import javax.inject.Inject

interface TransferCardDirections {
    suspend fun backTransferScreen()
    suspend fun navigateTransferVerifyScreen(token: String, data: UserCardData, amount: String)
    suspend fun navigateAddCardScreen()
}

class TransferCardDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : TransferCardDirections {
    override suspend fun backTransferScreen() =
        navigator.back()

    override suspend fun navigateTransferVerifyScreen(token: String, data: UserCardData, amount: String) =
        navigator.addScreen(TransferVerifyScreen(data, amount, token))

    override suspend fun navigateAddCardScreen() =
        navigator.addScreen(AddCardScreen())

}