package com.example.mobilebank.presentation.screens.transfer_card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.remote.request.TransferRequest
import com.example.mobilebank.domain.repositories.CardRepository
import com.example.mobilebank.domain.usecase.GetFeeUseCase
import com.example.mobilebank.domain.usecase.TransferUseCase
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import com.example.mobilebank.utils.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TransferCardViewModel @Inject constructor(
    private val directions: TransferCardDirections,
    private val transfer: TransferUseCase,
    private val getFee: GetFeeUseCase
) : ViewModel(), TransferCardContract.ViewModel {
    override val container = container<TransferCardContract.UIState, TransferCardContract.SideEffect>(TransferCardContract.UIState.GetFee(
        "", ""
    ))

    override fun onEventDispatcher(intent: TransferCardContract.Intent) = intent {
        when(intent) {
            TransferCardContract.Intent.BackTransferScreen -> {
                directions.backTransferScreen()
            }
            is TransferCardContract.Intent.TransferMoney -> {
                transfer(intent.senderId, intent.receiverPan, intent.amount + intent.percent).launch(viewModelScope) { result ->
                    result.onSuccess {
                        intent { directions.navigateTransferVerifyScreen(it.token, intent.data, intent.amount.toString()) }
                    }
                    result.onFailure {

                    }
                }
            }
            is TransferCardContract.Intent.GetFeeOfTransfer -> {
                getFee(intent.senderId, intent.receiverPan, intent.amount).launch(viewModelScope) { result ->
                    result.onSuccess { data ->
                       intent { reduce { TransferCardContract.UIState.GetFee(data.fee, data.amount) } }
                    }
                    result.onFailure {

                    }
                }
            }
            TransferCardContract.Intent.LoadCardsData -> {
                MyDataLoader.listStateFlow.launch(viewModelScope) {
                    intent {
                        reduce { TransferCardContract.UIState.SelectedCardData(it[0]) }
                    }

                }
            }
            TransferCardContract.Intent.OpenSelectMyCardDialog -> {
                postSideEffect(TransferCardContract.SideEffect.OpenSelectMyCardDialog)
            }
            is TransferCardContract.Intent.SelectedCardData -> {
               reduce { TransferCardContract.UIState.SelectedCardData(intent.data) }
            }
            TransferCardContract.Intent.NavigateAddCardScreen -> {
                directions.navigateAddCardScreen()
            }
        }
    }


}