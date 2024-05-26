package com.example.mobilebank.presentation.screens.settings

import androidx.lifecycle.ViewModel
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.utils.getCurrentLanguage
import com.example.mobilebank.utils.logger
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: LocalRepository,
    private val directions: SettingsDirections
) : ViewModel(), SettingsContract.ViewModel {
    override val container = container<SettingsContract.UIState, SettingsContract.SideEffect>(SettingsContract.UIState())

    override fun onEventDispatcher(intent: SettingsContract.Intent) = intent {
        when(intent) {
            SettingsContract.Intent.BackProfileScreen -> {
                directions.backProfileScreen()
            }
            SettingsContract.Intent.LoadAppSettings -> {
                reduce { SettingsContract.UIState(getCurrentLanguage() == "uz", repo.getBiometryUnlock()) }
            }
            is SettingsContract.Intent.SetBiometryUnlock -> {
                repo.setBiometryUnlock(intent.biometry)
                "SettingsContract".logger()
            }
        }
    }

}