package com.example.mobilebank.presentation.screens.main.pages.home

import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.data.model.Currency
import com.example.mobilebank.data.remote.response.GetCardResponse
import com.example.mobilebank.presentation.screens.add_card.AddCardContract
import kotlinx.coroutines.flow.StateFlow
import org.orbitmvi.orbit.ContainerHost

interface HomeContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        data class GetUIState(
            val phone: String = "+998900993936",
            val showBalance: Boolean = true,
        ) : UIState
        data class GetCardsData(
            var totalAmount: Long = 0,
            var ls: List<CardData> = listOf()
        ) : UIState
    }

    sealed interface SideEffect {
        data object OpenAddCardDialog : SideEffect
        data object NavigateToTransferPage : SideEffect
        data object NavigateToPaymentPage : SideEffect
    }

    sealed interface Intent {
        data object GetData : Intent
        data class ChangeShowBalanceState(
            var showBalance: Boolean
        ) : Intent
        data class OpenCardDetailsScreen(
            val data: CardData
        ) : Intent
        data object OpenProfileScreen : Intent
        data object OpenNotificationScreen : Intent
        data object GetAllCards : Intent
        data object OpenAddCardDialog : Intent
        data object OpenAddUzbCardScreen : Intent
        data object OpenWhatIsScreen : Intent
        data object NavigateToTransferPage : Intent
        data object NavigateToPaymentPage : Intent
        data object OpenMyCardsScreen : Intent
    }
}