package com.example.mobilebank.presentation.screens.settings

import com.example.mobilebank.navigation.AppNavigator
import javax.inject.Inject

interface SettingsDirections {
    suspend fun backProfileScreen()
}

class SettingsDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : SettingsDirections {

    override suspend fun backProfileScreen() =
        navigator.back()

}