package com.example.mobilebank.presentation.screens.my_cards

import com.example.mobilebank.data.model.CardData
import org.orbitmvi.orbit.ContainerHost

interface MyCardsContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState (
        var ls: List<CardData>
    )

    sealed interface SideEffect {
        data object OpenAddCardDialog : SideEffect
    }

    sealed interface Intent {
        data object BackToHomeScreen : Intent
        data object OpenAddCardDialog : Intent
        data class OpenCardDetailsScreen(
            val data: CardData
        ) : Intent
        data object OpenAddCardScreen : Intent
        data object GetAllCards : Intent
    }
}