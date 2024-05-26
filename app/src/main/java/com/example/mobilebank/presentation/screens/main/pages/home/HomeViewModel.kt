package com.example.mobilebank.presentation.screens.main.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.MyMapper.toUIData
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.usecase.GetAllCardsUseCase
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: LocalRepository,
    private val directions: HomeDirections
) : ViewModel(), HomeContract.ViewModel {
    override val container = container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState.GetUIState())

    override fun onEventDispatcher(intent: HomeContract.Intent) = intent {
        when(intent) {
            HomeContract.Intent.GetData -> {
                reduce { HomeContract.UIState.GetUIState(repo.getPhone(), repo.showBalance()) }
            }
            is HomeContract.Intent.ChangeShowBalanceState -> {
                repo.changeShowBalanceState(intent.showBalance)
            }
            HomeContract.Intent.OpenProfileScreen -> {
                directions.navigateProfileScreen()
            }
            HomeContract.Intent.OpenNotificationScreen -> {
                directions.navigateNotificationsScreen()
            }
            HomeContract.Intent.GetAllCards -> {
                MyDataLoader.listStateFlow.launch(viewModelScope) { list ->
                    MyDataLoader.totalAmountStateFlow.launch(viewModelScope) { amount ->
                        intent{
                            reduce {
                                HomeContract.UIState.GetCardsData(amount, list)
                            }
                        }
                    }
                }


            }
            HomeContract.Intent.OpenAddCardDialog -> {
                postSideEffect(HomeContract.SideEffect.OpenAddCardDialog)
            }
            HomeContract.Intent.OpenAddUzbCardScreen -> {
                directions.navigateAddUzbCardScreen()
            }
            is HomeContract.Intent.OpenCardDetailsScreen -> {
               directions.navigateCardDetailsScreen(intent.data)
            }
            HomeContract.Intent.OpenMyCardsScreen -> {
               directions.navigateMyCardsScreen()
            }
            HomeContract.Intent.OpenWhatIsScreen -> {
                directions.navigateWhatIsScreen()
            }
            HomeContract.Intent.NavigateToPaymentPage -> {
                postSideEffect(HomeContract.SideEffect.NavigateToPaymentPage)
            }
            HomeContract.Intent.NavigateToTransferPage -> {
                postSideEffect(HomeContract.SideEffect.NavigateToTransferPage)
            }
        }
    }

}