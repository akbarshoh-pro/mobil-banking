package com.example.mobilebank.presentation.screens.what_is

import com.example.mobilebank.navigation.AppNavigator
import javax.inject.Inject

interface WhatIsDirections {
    suspend fun backHomeScreen()
}

class WhatIsDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : WhatIsDirections {
    override suspend fun backHomeScreen() =
        navigator.back()

}