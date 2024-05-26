package com.example.mobilebank.presentation.screens.card_details

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.presentation.screens.main.pages.home.HomeContract
import org.orbitmvi.orbit.ContainerHost

interface CardDetailsContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    class UIState

    sealed interface SideEffect {
        data object NavigateToTransferPage : SideEffect
        data object NavigateToPaymentPage : SideEffect
        data class OpenDeleteCardDialog(
            val id: String
        ) : SideEffect
    }

    sealed interface Intent {
        data object BackToHomeScreen : Intent
        data class OpenDeleteCardDialog(
            val id: String
        ) : Intent
        data class DeleteCard(
            val id: String
        ) : Intent
        data class NavigateToCardTheme(
            val data: CardData
        ) : Intent
        data object NavigateToTransferPage : Intent
        data object NavigateToPaymentPage : Intent
    }
}