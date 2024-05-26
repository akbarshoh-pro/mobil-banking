package com.example.mobilebank.presentation.screens.transfer_verify

import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.transfer_success.TransferSuccessScreen
import javax.inject.Inject

interface TransferVerifyDirections {
    suspend fun navigateTransferSuccessScreen(data: UserCardData, amount: String)
    suspend fun backToTransferCardScreen()
}
class TransferVerifyDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : TransferVerifyDirections {

    override suspend fun navigateTransferSuccessScreen(data: UserCardData, amount: String) {
        navigator.addScreen(TransferSuccessScreen(data, amount.toLong()))
    }

    override suspend fun backToTransferCardScreen() =
        navigator.back()

}