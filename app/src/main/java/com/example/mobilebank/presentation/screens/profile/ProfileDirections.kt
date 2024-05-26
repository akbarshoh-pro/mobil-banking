package com.example.mobilebank.presentation.screens.profile

import cafe.adriel.voyager.core.screen.Screen
import com.example.mobilebank.navigation.AppNavigator
import com.example.mobilebank.presentation.screens.auth.AuthScreen
import com.example.mobilebank.presentation.screens.map.MapScreen
import com.example.mobilebank.presentation.screens.settings.SettingsScreen
import com.example.mobilebank.presentation.screens.splash.SplashScreen
import com.example.mobilebank.presentation.screens.update_data.UpdateDataScreen
import javax.inject.Inject

interface ProfileDirections {
    suspend fun navigateAuthScreen()
    suspend fun navigateMapScreen()
    suspend fun navigateSettingsScreen()
    suspend fun navigateUpdateDataScreen()
    suspend fun backHomeScreen()
}

class ProfileDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : ProfileDirections {

    override suspend fun navigateAuthScreen() {
        navigator.replaceAll(AuthScreen())
    }

    override suspend fun navigateMapScreen() =
        navigator.addScreen(MapScreen())

    override suspend fun navigateSettingsScreen() =
        navigator.addScreen(SettingsScreen())

    override suspend fun navigateUpdateDataScreen() =
        navigator.addScreen(UpdateDataScreen())

    override suspend fun backHomeScreen() =
        navigator.back()

}