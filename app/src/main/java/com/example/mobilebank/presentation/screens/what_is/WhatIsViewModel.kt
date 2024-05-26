package com.example.mobilebank.presentation.screens.what_is

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class WhatIsViewModel @Inject constructor(
    private val directions: WhatIsDirections
) : ViewModel(), WhatIsContract.ViewModel {
    override val container = container<WhatIsContract.UIState, WhatIsContract.SideEffect>(WhatIsContract.UIState())

    override fun onEventDispatcher(intent: WhatIsContract.Intent) = intent {
        when(intent) {
            WhatIsContract.Intent.BackHomeScreen -> {
                directions.backHomeScreen()
            }
        }
    }
}