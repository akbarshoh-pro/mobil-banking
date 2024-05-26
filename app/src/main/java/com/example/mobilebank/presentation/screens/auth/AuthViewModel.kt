package com.example.mobilebank.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.model.SmsRequestType
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.usecase.LoginUseCase
import com.example.mobilebank.domain.usecase.RegisterUseCase
import com.example.mobilebank.utils.launch
import com.example.mobilebank.utils.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val directions: AuthDirections,
    private val repo: LocalRepository,
    private val login: LoginUseCase,
    private val register: RegisterUseCase
) : ViewModel(), AuthContract.ViewModel {
    override val container = container<AuthContract.UIState, AuthContract.SideEffect>(AuthContract.UIState())

    override fun onEventDispatcher(intent: AuthContract.Intent) = intent {
        when(intent) {
            is AuthContract.Intent.RegisterOrCreate -> {
                login(intent.phone)
                    .launch(viewModelScope) { result ->
                        result.onSuccess { data ->
                            repo.setPhone(intent.phone)
                            intent { directions.navigateSmsScreen(SmsRequestType(data.token, true)) }
                        }
                        result.onFailure {
                            register(intent.phone)
                                .launch(viewModelScope) { result ->
                                    result.onSuccess { data ->
                                        repo.setPhone(intent.phone)
                                        intent { directions.navigateSmsScreen(SmsRequestType(data.token, false)) }
                                    }
                                    result.onFailure {
                                        "${it.message}".logger()
                                    }
                                }
                        }
                    }



            }
            AuthContract.Intent.OpenChangeLanguageDialog -> {
                postSideEffect(AuthContract.SideEffect.OpenChangeLanguageDialog)
            }
        }
    }

}