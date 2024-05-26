package com.example.mobilebank.presentation.screens.daily

import androidx.lifecycle.ViewModel
import com.example.mobilebank.domain.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DailyPinViewModel @Inject constructor(
    private val directions: DailyPinDirections,
    private val repo: LocalRepository
) : ViewModel(), DailyPinContract.ViewModel {
    override val container = container<DailyPinContract.UIState, DailyPinContract.SideEffect>(DailyPinContract.UIState())

    override fun onEventDispatcher(intent: DailyPinContract.Intent) = intent {
        when(intent) {
            DailyPinContract.Intent.NavigateToMainScreen -> {
                directions.navigateMainScreen()
            }
            DailyPinContract.Intent.GetData -> {
                reduce { DailyPinContract.UIState(repo.getPhone(), repo.getPin()) }
            }
            DailyPinContract.Intent.OpenUpdatePinDialog -> {
                postSideEffect(DailyPinContract.SideEffect.OpenUpdatePinDialog)
            }
            DailyPinContract.Intent.UserUnRegister -> {
                repo.userUnRegister()
                directions.navigateAuthScreen()
            }
        }
    }

}