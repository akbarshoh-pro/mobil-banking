package com.example.mobilebank.presentation.screens.settings

import org.orbitmvi.orbit.ContainerHost

interface SettingsContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val lang: Boolean = true,
        val biometry: Boolean = false
    )

    sealed interface SideEffect

    sealed interface Intent {
        data object BackProfileScreen : Intent
        data object LoadAppSettings : Intent
        data class SetBiometryUnlock (
            val biometry: Boolean
        ) : Intent
    }
}