package com.example.mobilebank.presentation.screens.update_data

import org.orbitmvi.orbit.ContainerHost

interface UpdateDataContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect

    sealed interface Intent {
        data class UpdateData(
            val firstName: String,
            val lastName: String,
            val bornDate: String
        ) : Intent

        data object BackProfileScreen : Intent
    }
}