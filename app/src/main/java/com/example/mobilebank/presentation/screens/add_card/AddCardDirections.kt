package com.example.mobilebank.presentation.screens.add_card

import com.example.mobilebank.navigation.AppNavigator
import javax.inject.Inject

interface AddCardDirections {
    suspend fun backToMain()
}

class AddCardDirectionsImp @Inject constructor(
    private val navigator: AppNavigator
) : AddCardDirections {
    override suspend fun backToMain() =
        navigator.back()

}