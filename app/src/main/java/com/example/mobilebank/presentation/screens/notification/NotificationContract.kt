package com.example.mobilebank.presentation.screens.notification

import org.orbitmvi.orbit.ContainerHost

interface NotificationContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect

    sealed interface Intent {
        data object BackMainScreen : Intent
    }
}