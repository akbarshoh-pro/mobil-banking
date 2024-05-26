package com.example.mobilebank.presentation.screens.daily

import org.orbitmvi.orbit.ContainerHost

interface DailyPinContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        var phone: String = "",
        var code: String = ""
    )

    sealed interface SideEffect {
        data object OpenUpdatePinDialog : SideEffect
    }

    sealed interface Intent{
        data object NavigateToMainScreen : Intent
        data object GetData : Intent
        data object OpenUpdatePinDialog : Intent
        data object UserUnRegister : Intent
    }
}