package com.example.mobilebank.presentation.screens.main.pages.home

import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.add_card.AddCardScreen
import com.example.mobilebank.presentation.screens.card_details.CardDetailsScreen
import com.example.mobilebank.presentation.screens.my_cards.MyCardsScreen
import com.example.mobilebank.presentation.screens.notification.NotificationScreen
import com.example.mobilebank.presentation.screens.profile.ProfileScreen
import com.example.mobilebank.presentation.screens.what_is.WhatIsThisScreen
import javax.inject.Inject

interface HomeDirections {
    suspend fun navigateNotificationsScreen()
    suspend fun navigateProfileScreen()
    suspend fun navigateAddUzbCardScreen()
    suspend fun navigateWhatIsScreen()
    suspend fun navigateCardDetailsScreen(data: CardData)
    suspend fun navigateMyCardsScreen()
}

class HomeDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : HomeDirections {

    override suspend fun navigateNotificationsScreen() =
        navigator.addScreen(NotificationScreen())


    override suspend fun navigateProfileScreen() =
        navigator.addScreen(ProfileScreen())

    override suspend fun navigateAddUzbCardScreen() =
        navigator.addScreen(AddCardScreen())

    override suspend fun navigateWhatIsScreen() =
        navigator.addScreen(WhatIsThisScreen())

    override suspend fun navigateCardDetailsScreen(data: CardData) =
        navigator.addScreen(CardDetailsScreen(data))

    override suspend fun navigateMyCardsScreen() =
        navigator.addScreen(MyCardsScreen())

}