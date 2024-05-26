package com.example.mobilebank.presentation.screens.splash

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.core.model.ScreenModel
import com.example.mobilebank.domain.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val directions: SplashDirections,
    private val repo: LocalRepository
) : ViewModel(), SplashContract.ViewModel, ScreenModel {
    override val container =
        container<SplashContract.UIState, SplashContract.SideEffect>(SplashContract.UIState())

    override fun onEventDispatcher(intent: SplashContract.Intent) = intent {
        when (intent) {
            SplashContract.Intent.NavigateTo -> {
                if (!repo.userIsRegistered()) {
                    directions.navigateAuthScreen()
                } else {
                    directions.navigatePinScreen()
                }
            }
        }
    }

}