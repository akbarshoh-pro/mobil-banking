package com.example.mobilebank.presentation.screens.my_cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.MyMapper.toUIData
import com.example.mobilebank.domain.usecase.GetAllCardsUseCase
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyCardsViewModel @Inject constructor(
    private val directions: MyCardsDirections,
    private val getAllCards: GetAllCardsUseCase
) : ViewModel(), MyCardsContract.ViewModel {
    override val container = container<MyCardsContract.UIState, MyCardsContract.SideEffect>(MyCardsContract.UIState(listOf()))

    override fun onEventDispatcher(intent: MyCardsContract.Intent) = intent {
        when(intent) {
            MyCardsContract.Intent.BackToHomeScreen -> {
                directions.backHomeScreen()
            }
            MyCardsContract.Intent.OpenAddCardDialog -> {
                postSideEffect(MyCardsContract.SideEffect.OpenAddCardDialog)
            }
            MyCardsContract.Intent.OpenAddCardScreen -> {
                directions.navigateAddCardScreen()
            }
            is MyCardsContract.Intent.OpenCardDetailsScreen -> {
                directions.navigateCardDetails(intent.data)
            }
            MyCardsContract.Intent.GetAllCards -> {
                getAllCards().launch(viewModelScope) { result ->
                    result.onSuccess {
                         intent{ reduce { MyCardsContract.UIState(it.map { it.toUIData() }) } }
                    }
                    result.onFailure {  }
                }
            }
        }
    }

}