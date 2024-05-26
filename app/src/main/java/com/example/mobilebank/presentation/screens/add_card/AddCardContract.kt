package com.example.mobilebank.presentation.screens.add_card

import org.orbitmvi.orbit.ContainerHost

interface AddCardContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        var requestIsSuccess: Boolean
    )

    sealed interface SideEffect

    sealed interface Intent {
        data object BackToMainScreen : Intent
        data class AddCard(
            val cardNumber: String,
            val year: String,
            val month: String
        ) : Intent
    }
}