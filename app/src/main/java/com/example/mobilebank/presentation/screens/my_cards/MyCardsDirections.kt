package com.example.mobilebank.presentation.screens.my_cards

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.add_card.AddCardScreen
import com.example.mobilebank.presentation.screens.card_details.CardDetailsScreen
import javax.inject.Inject

interface MyCardsDirections {
    suspend fun backHomeScreen()
    suspend fun navigateAddCardScreen()
    suspend fun navigateCardDetails(data: CardData)
}

class MyCardsDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : MyCardsDirections {
    override suspend fun backHomeScreen() =
        navigator.back()

    override suspend fun navigateAddCardScreen() =
        navigator.addScreen(AddCardScreen())

    override suspend fun navigateCardDetails(data: CardData) =
        navigator.addScreen(CardDetailsScreen(data))
}