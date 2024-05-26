package com.example.mobilebank.presentation.screens.card_theme

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.card_details.CardDetailsScreen
import javax.inject.Inject

interface CardThemeDirections {
    suspend fun backCardDetails(data: CardData)
}

class CardThemeDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : CardThemeDirections {
    override suspend fun backCardDetails(data: CardData) =
        navigator.replaceScreen(CardDetailsScreen(data))

}