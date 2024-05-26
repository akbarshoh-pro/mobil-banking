package com.example.mobilebank.presentation.screens.sms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.usecase.LoginResendUseCase
import com.example.mobilebank.domain.usecase.LoginVerifyUseCase
import com.example.mobilebank.domain.usecase.RegisterResendUseCase
import com.example.mobilebank.domain.usecase.RegisterVerifyUseCase
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val directions: SmsDirections,
    private val repo: LocalRepository,
    private val loginVerify: LoginVerifyUseCase,
    private val registerVerify: RegisterVerifyUseCase,
    private val loginResend: LoginResendUseCase,
    private val registerResend: RegisterResendUseCase
) : ViewModel(), SmsContract.ViewModel {
    override val container = container<SmsContract.UIState, SmsContract.SideEffect>(SmsContract.UIState.UserPhoneNumber(""))

    override fun onEventDispatcher(intent: SmsContract.Intent) = intent {
        when(intent) {
            SmsContract.Intent.BackToScreen -> {
                directions.backToAuthScreen()
            }
            is SmsContract.Intent.NavigateToCreateScreen -> {
                if (intent.type) {
                    loginVerify(intent.token, intent.code)
                        .launch(viewModelScope) { result ->
                            result.onSuccess {
                                repo.setRefreshToken(it.refreshToken)
                                repo.setAccessToken(it.accessToken)
                                intent{ directions.navigateCreatePinScreen() }
                            }
                            result.onFailure {
                                intent { reduce { SmsContract.UIState.ErrorMes } }
                            }
                        }
                } else {
                    registerVerify(intent.token, intent.code)
                        .launch(viewModelScope) { result ->
                            result.onSuccess {
                                repo.setRefreshToken(it.refreshToken)
                                repo.setAccessToken(it.accessToken)
                                intent{ directions.navigateCreatePinScreen() }
                            }
                            result.onFailure {
                                intent { reduce { SmsContract.UIState.ErrorMes } }
                            }
                        }
                }
            }
            SmsContract.Intent.GetData -> {
                 reduce { SmsContract.UIState.UserPhoneNumber(repo.getPhone()) }
            }
            is SmsContract.Intent.ResendCode -> {
                if (intent.type) {
                    loginResend(intent.token)
                        .launch(viewModelScope) { result ->
                            result.onSuccess {
                                intent { reduce { SmsContract.UIState.RequestToken(it.token) } }
                            }
                            result.onFailure {}
                        }
                } else {
                    registerResend(intent.token)
                        .launch(viewModelScope) { result ->
                            result.onSuccess {
                                intent { reduce { SmsContract.UIState.RequestToken(it.token) } }
                            }
                            result.onFailure {}
                        }
                }
            }
        }
    }

}