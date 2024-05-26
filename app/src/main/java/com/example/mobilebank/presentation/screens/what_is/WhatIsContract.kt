package com.example.mobilebank.presentation.screens.what_is

import org.orbitmvi.orbit.ContainerHost

interface WhatIsContract {
        interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect

    sealed interface Intent {
        data object BackHomeScreen : Intent
    }
}