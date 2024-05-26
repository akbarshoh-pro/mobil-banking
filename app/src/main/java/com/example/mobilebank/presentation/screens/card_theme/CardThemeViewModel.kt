package com.example.mobilebank.presentation.screens.card_theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.usecase.UpdateCardUseCase
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CardThemeViewModel @Inject constructor(
    private val directions: CardThemeDirections,
    private val updateCard: UpdateCardUseCase
) : ViewModel(), CardThemeContract.ViewModel {
    override val container = container<CardThemeContract.UIState, CardThemeContract.SideEffect>(CardThemeContract.UIState())

    override fun onEventDispatcher(intent: CardThemeContract.Intent) = intent {
        when(intent) {
            is CardThemeContract.Intent.BackCardDetails -> {
                directions.backCardDetails(intent.data)
            }
            is CardThemeContract.Intent.UpdateCard -> {
                updateCard(intent.data.id, intent.data.name, intent.data.themeType, intent.data.isVisible)
                    .launch(viewModelScope) { result ->
                        result.onSuccess {
                            MyDataLoader.loadCardsData()
                            intent{ directions.backCardDetails(intent.data) }
                        }
                        result.onFailure {  }
                    }
            }
        }
    }

}