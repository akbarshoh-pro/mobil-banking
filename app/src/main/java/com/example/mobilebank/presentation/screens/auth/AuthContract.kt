package com.example.mobilebank.presentation.screens.auth

import org.orbitmvi.orbit.ContainerHost

interface AuthContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect {
        data object OpenChangeLanguageDialog : SideEffect
    }

    sealed interface Intent{
        data class RegisterOrCreate(
            val phone: String
        ) : Intent
        data object OpenChangeLanguageDialog : Intent
    }

}