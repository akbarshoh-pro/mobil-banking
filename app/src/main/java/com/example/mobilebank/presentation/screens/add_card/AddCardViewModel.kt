package com.example.mobilebank.presentation.screens.add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.usecase.AddCardUseCase
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val addCard: AddCardUseCase,
    private val directions: AddCardDirections
) : ViewModel(), AddCardContract.ViewModel {
    override val container = container<AddCardContract.UIState, AddCardContract.SideEffect>(AddCardContract.UIState(true))

    override fun onEventDispatcher(intent: AddCardContract.Intent) = intent {
        when(intent) {
            AddCardContract.Intent.BackToMainScreen -> {
                directions.backToMain()
            }
            is AddCardContract.Intent.AddCard -> {
                addCard(intent.cardNumber, intent.year, intent.month.toInt().toString())
                    .launch(viewModelScope) { result ->
                        result.onSuccess {
                            intent{ directions.backToMain() }
                        }
                        result.onFailure {
                            container.stateFlow.value.requestIsSuccess = false
                        }
                    }
            }
        }
    }

}