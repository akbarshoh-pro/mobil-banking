package com.example.mobilebank.presentation.screens.map

import com.example.mobilebank.navigation.AppNavigator
import javax.inject.Inject

interface MapDirections {
    suspend fun backProfileScreen()
}

class MapDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : MapDirections {
    override suspend fun backProfileScreen() {
        navigator.back()
    }

}