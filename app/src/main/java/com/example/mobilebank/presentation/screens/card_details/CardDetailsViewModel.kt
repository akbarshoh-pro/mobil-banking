package com.example.mobilebank.presentation.screens.card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.usecase.DeleteCardUseCase
import com.example.mobilebank.presentation.screens.main.pages.home.HomeContract
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val directions: CardDetailsDirections,
    private val deleteCard: DeleteCardUseCase
) : ViewModel(), CardDetailsContract.ViewModel {
    override val container = container<CardDetailsContract.UIState, CardDetailsContract.SideEffect>(CardDetailsContract.UIState())

    override fun onEventDispatcher(intent: CardDetailsContract.Intent) = intent {
        when(intent) {
            CardDetailsContract.Intent.BackToHomeScreen -> {
                directions.backToHomeScreen()
            }
            is CardDetailsContract.Intent.OpenDeleteCardDialog -> {
                postSideEffect(CardDetailsContract.SideEffect.OpenDeleteCardDialog(intent.id))
            }
            is CardDetailsContract.Intent.DeleteCard -> {
                deleteCard(intent.id).launch(viewModelScope) { result ->
                    result.onSuccess {
                        intent{ directions.backToHomeScreen() }
                    }
                    result.onFailure {

                    }
                }
            }
            CardDetailsContract.Intent.NavigateToPaymentPage -> {
                postSideEffect(CardDetailsContract.SideEffect.NavigateToPaymentPage)
            }
            CardDetailsContract.Intent.NavigateToTransferPage -> {
                postSideEffect(CardDetailsContract.SideEffect.NavigateToTransferPage)
            }
            is CardDetailsContract.Intent.NavigateToCardTheme -> {
                directions.navigateCardTheme(intent.data)
            }
        }
    }

}