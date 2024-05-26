package com.example.mobilebank.presentation.screens.profile

import com.example.mobilebank.data.model.UserData
import org.orbitmvi.orbit.ContainerHost

interface ProfileContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        val data: UserData = UserData("", "", 0, "")
    )

    sealed interface SideEffect {
        data object OpenAboutDialog : SideEffect
        data object OpenOffersDialog : SideEffect
        data object OpenRateDialog : SideEffect
        data object OpenLogOutDialog : SideEffect
    }

    sealed interface Intent {
        data object OpenLogOutDialog : Intent
        data object BackScreen : Intent
        data object GetUserData : Intent
        data object OpenAboutDialog : Intent
        data object OpenOffersDialog : Intent
        data object OpenRateDialog : Intent
        data object NavigateMapScreen : Intent
        data object NavigateSettingsScreen : Intent
        data object NavigateUpdateDataScreen : Intent
        data object LogOut : Intent
    }
}