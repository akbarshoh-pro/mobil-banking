package com.example.mobilebank.presentation.screens.confirm

import androidx.lifecycle.ViewModel
import com.example.mobilebank.domain.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ConfirmPinViewModel @Inject constructor(
    private val repo: LocalRepository,
    private val directions: ConfirmPinDirections
) : ViewModel(), ConfirmPinContract.ViewModel {
    override val container = container<ConfirmPinContract.UIState, ConfirmPinContract.SideEffect>(ConfirmPinContract.UIState())

    override fun onEventDispatcher(intent: ConfirmPinContract.Intent) = intent {
        when(intent) {
            is ConfirmPinContract.Intent.NavigateToMain -> {
                directions.navigateMainScreen()
                repo.userRegister()
            }
            ConfirmPinContract.Intent.GetData -> {
                reduce { ConfirmPinContract.UIState(repo.getPhone(), repo.getPin()) }
            }
        }
    }

}