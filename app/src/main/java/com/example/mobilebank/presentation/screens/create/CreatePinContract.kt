package com.example.mobilebank.presentation.screens.create

import org.orbitmvi.orbit.ContainerHost

interface CreatePinContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        var phone: String = ""
    )

    sealed interface SideEffect

    sealed interface Intent{
        data class NavigateConfirmScreen(
            val pin: String
        ) : Intent
        data object GetData : Intent
    }
}