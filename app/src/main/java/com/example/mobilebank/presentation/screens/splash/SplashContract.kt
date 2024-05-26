package com.example.mobilebank.presentation.screens.splash

import org.orbitmvi.orbit.ContainerHost

interface SplashContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect

    sealed interface Intent {
        data object NavigateTo : Intent
    }
}