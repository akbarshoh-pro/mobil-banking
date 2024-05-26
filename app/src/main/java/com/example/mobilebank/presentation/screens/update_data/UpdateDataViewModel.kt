package com.example.mobilebank.presentation.screens.update_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.usecase.UpdateDataUseCase
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UpdateDataViewModel @Inject constructor(
    private val directions: UpdateDataDirections,
    private val updateData: UpdateDataUseCase
) : ViewModel(), UpdateDataContract.ViewModel {
    override val container = container<UpdateDataContract.UIState, UpdateDataContract.SideEffect>(UpdateDataContract.UIState())

    override fun onEventDispatcher(intent: UpdateDataContract.Intent) = intent {
        when(intent) {
            UpdateDataContract.Intent.BackProfileScreen -> {
                directions.backToProfileScreen()
            }
            is UpdateDataContract.Intent.UpdateData -> {
                updateData(intent.firstName, intent.lastName, intent.bornDate)
                    .launch(viewModelScope) { result ->
                        result.onSuccess { intent { directions.backToProfileScreen() } }
                        result.onFailure {  }
                    }
            }
        }
    }
}