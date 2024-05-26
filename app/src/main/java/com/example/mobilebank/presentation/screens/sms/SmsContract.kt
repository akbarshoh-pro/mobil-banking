package com.example.mobilebank.presentation.screens.sms

import org.orbitmvi.orbit.ContainerHost

interface SmsContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        data class RequestToken(
            var token: String
        ) : UIState
        data object ErrorMes : UIState
        data class UserPhoneNumber(
            var phone: String
        ) : UIState
    }

    sealed interface SideEffect

    sealed interface Intent{
        data class NavigateToCreateScreen(
            val token: String,
            val code: String,
            val type: Boolean
        ) : Intent
        data object BackToScreen : Intent
        data object GetData : Intent
        data class ResendCode (
            val type: Boolean,
            val token: String
        ) : Intent
    }
}