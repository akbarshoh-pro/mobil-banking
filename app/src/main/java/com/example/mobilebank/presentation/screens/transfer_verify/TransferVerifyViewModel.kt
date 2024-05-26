package com.example.mobilebank.presentation.screens.transfer_verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.local.entity.LastTransferUserEntity
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.usecase.TransferResendUseCase
import com.example.mobilebank.domain.usecase.TransferVerifyUseCase
import com.example.mobilebank.presentation.screens.sms.SmsContract
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TransferVerifyViewModel @Inject constructor(
    private val directions: TransferVerifyDirections,
    private val repo: LocalRepository,
    private val resendCode: TransferResendUseCase,
    private val transferVerify: TransferVerifyUseCase
) : ViewModel(), TransferVerifyContract.ViewModel {
    override fun onEventDispatcher(intent: TransferVerifyContract.Intent) = intent {
        when(intent) {
            TransferVerifyContract.Intent.BackToTransferCardScreen -> {
                directions.backToTransferCardScreen()
            }
            TransferVerifyContract.Intent.GetData -> {
                reduce { TransferVerifyContract.UIState.UserPhoneNumber(repo.getPhone()) }
            }
            is TransferVerifyContract.Intent.ResendCode -> {
                resendCode(intent.token).launch(viewModelScope) { result ->
                    result.onSuccess {
                        intent { reduce { TransferVerifyContract.UIState.RequestToken(it.token) } }
                    }
                    result.onFailure {}
                }
            }
            is TransferVerifyContract.Intent.NavigateTransferSuccessScreen -> {
                transferVerify(intent.token, intent.code).launch(viewModelScope) { result ->
                    result.onSuccess {
                        intent{
                            MyDataLoader.loadCardsData()
                            repo.addUser(
                                LastTransferUserEntity(
                                    intent.data.pan,
                                    intent.data.owner
                                )
                            )
                        }
                        intent{ directions.navigateTransferSuccessScreen(intent.data, intent.amount) }
                    }
                    result.onFailure {
                        intent { reduce { TransferVerifyContract.UIState.ErrorMes } }
                    }
                }
            }
        }
    }

    override val container = container<TransferVerifyContract.UIState, TransferVerifyContract.SideEffect>(
        TransferVerifyContract.UIState.UserPhoneNumber("")
    )

}