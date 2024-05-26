package com.example.mobilebank.presentation.screens.confirm

import org.orbitmvi.orbit.ContainerHost

interface ConfirmPinContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        val phone: String = "",
        val code: String = ""
    )

    sealed interface SideEffect

    sealed interface Intent{
        data object NavigateToMain: Intent
        data object GetData : Intent
    }
}