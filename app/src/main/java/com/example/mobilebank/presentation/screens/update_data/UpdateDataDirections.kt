package com.example.mobilebank.presentation.screens.update_data

import com.example.mobilebank.navigation.AppNavigator
import javax.inject.Inject

interface UpdateDataDirections {
    suspend fun backToProfileScreen()
}

class UpdateDataDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : UpdateDataDirections {
    override suspend fun backToProfileScreen() =
        navigator.back()
}