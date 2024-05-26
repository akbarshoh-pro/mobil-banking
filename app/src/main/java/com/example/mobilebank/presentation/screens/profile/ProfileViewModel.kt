package com.example.mobilebank.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: LocalRepository,
    private val directions: ProfileDirections
) : ViewModel(), ProfileContract.ViewModel {
    override val container = container<ProfileContract.UIState, ProfileContract.SideEffect>(ProfileContract.UIState())

    override fun onEventDispatcher(intent: ProfileContract.Intent) = intent {
        when(intent) {
            ProfileContract.Intent.BackScreen -> {
                directions.backHomeScreen()
            }
            ProfileContract.Intent.GetUserData -> {
                MyDataLoader.userInfoStateFlow.launch(viewModelScope) { data ->
                    intent { reduce { ProfileContract.UIState(data) } }
                }
            }
            ProfileContract.Intent.OpenAboutDialog -> {
                postSideEffect(ProfileContract.SideEffect.OpenAboutDialog)
            }
            ProfileContract.Intent.OpenOffersDialog -> {
                postSideEffect(ProfileContract.SideEffect.OpenOffersDialog)
            }
            ProfileContract.Intent.OpenRateDialog -> {
                postSideEffect(ProfileContract.SideEffect.OpenRateDialog)
            }
            ProfileContract.Intent.LogOut -> {
                repo.userUnRegister()
                repo.deleteAllItems()
                directions.navigateAuthScreen()
            }
            ProfileContract.Intent.NavigateMapScreen -> {
                directions.navigateMapScreen()
            }
            ProfileContract.Intent.NavigateSettingsScreen -> {
                directions.navigateSettingsScreen()
            }
            ProfileContract.Intent.OpenLogOutDialog -> {
                postSideEffect(ProfileContract.SideEffect.OpenLogOutDialog)
            }
            ProfileContract.Intent.NavigateUpdateDataScreen -> {
                directions.navigateUpdateDataScreen()
            }
        }
    }

}