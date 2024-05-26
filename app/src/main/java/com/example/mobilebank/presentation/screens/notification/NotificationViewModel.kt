package com.example.mobilebank.presentation.screens.notification

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val directions: NotificationDirections
) : ViewModel(), NotificationContract.ViewModel {
    override val container = container<NotificationContract.UIState, NotificationContract.SideEffect>(NotificationContract.UIState())

    override fun onEventDispatcher(intent: NotificationContract.Intent) = intent {
        when(intent) {
            NotificationContract.Intent.BackMainScreen -> {
                directions.backMainScreen()
            }
        }
    }

}