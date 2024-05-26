package com.example.mobilebank.presentation.screens.main.pages.transfer

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.add_card.AddCardScreen
import com.example.mobilebank.presentation.screens.to_mycard.TransferMyCardScreen
import com.example.mobilebank.presentation.screens.transfer_card.TransferCardScreen
import javax.inject.Inject

interface TransferDirections {
    suspend fun navigateTransferCard(
        userCardData: UserCardData
    )
    suspend fun navigateAddCardScreen()

    suspend fun navigateTransferMyCardScreen(list: List<CardData>, data: UserCardData)
}


class TransferDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : TransferDirections {
    override suspend fun navigateTransferCard(userCardData: UserCardData) =
        navigator.addScreen(TransferCardScreen(userCardData))

    override suspend fun navigateAddCardScreen() =
        navigator.addScreen(AddCardScreen())

    override suspend fun navigateTransferMyCardScreen(list: List<CardData>, data: UserCardData) =
        navigator.addScreen(TransferMyCardScreen(list, data))
}
