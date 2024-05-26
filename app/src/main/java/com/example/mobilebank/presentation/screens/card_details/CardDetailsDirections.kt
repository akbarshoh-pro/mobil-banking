package com.example.mobilebank.presentation.screens.card_details

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.card_theme.CardThemeScreen
import javax.inject.Inject

interface CardDetailsDirections {
    suspend fun backToHomeScreen()
    suspend fun navigateCardTheme(data: CardData)
}

class CardDetailsDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : CardDetailsDirections {
    override suspend fun backToHomeScreen() =
        navigator.back()

    override suspend fun navigateCardTheme(data: CardData) =
        navigator.replaceScreen(CardThemeScreen(data))
}