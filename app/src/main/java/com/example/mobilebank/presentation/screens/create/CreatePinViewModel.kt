package com.example.mobilebank.presentation.screens.create

import androidx.lifecycle.ViewModel
import com.example.mobilebank.domain.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreatePinViewModel @Inject constructor(
    private val directions: CreatePinDirections,
    private val repo: LocalRepository
): ViewModel(), CreatePinContract.ViewModel {
    override val container = container<CreatePinContract.UIState, CreatePinContract.SideEffect>(CreatePinContract.UIState())

    override fun onEventDispatcher(intent: CreatePinContract.Intent) = intent {
        when(intent) {
            is CreatePinContract.Intent.NavigateConfirmScreen -> {
                repo.setPin(intent.pin)
                directions.navigateConfirmPinScreen()
            }
            CreatePinContract.Intent.GetData -> {
                reduce { CreatePinContract.UIState(repo.getPhone()) }
            }
        }
    }

}