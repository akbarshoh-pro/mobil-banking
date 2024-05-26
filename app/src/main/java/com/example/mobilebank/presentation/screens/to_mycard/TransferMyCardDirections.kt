package com.example.mobilebank.presentation.screens.to_mycard

import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.transfer_verify.TransferVerifyScreen
import javax.inject.Inject

interface TransferMyCardDirections {
    suspend fun backTransferScreen()
    suspend fun navigateTransferVerifyScreen(token: String, data: UserCardData, amount: String)
}

class TransferMyCardDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : TransferMyCardDirections {
    override suspend fun backTransferScreen() =
        navigator.back()

    override suspend fun navigateTransferVerifyScreen(
        token: String,
        data: UserCardData,
        amount: String
    ) = navigator.addScreen(TransferVerifyScreen(data, amount, token))

}