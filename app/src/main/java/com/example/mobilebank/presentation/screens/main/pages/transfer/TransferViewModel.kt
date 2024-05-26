package com.example.mobilebank.presentation.screens.main.pages.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilebank.data.model.UserCardData
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.usecase.GetCardOwnerUseCase
import com.example.mobilebank.utils.MyDataLoader
import com.example.mobilebank.utils.launch
import com.example.mobilebank.utils.logger
import com.example.mobilebank.utils.toUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val getCardOwner: GetCardOwnerUseCase,
    private val repo: LocalRepository,
    private val directions: TransferDirections
) : ViewModel(), TransferContract.ViewModel {
    override val container = container<TransferContract.UIState, TransferContract.SideEffect>(
        TransferContract.UIState.SearchedItems()
    )

    override fun onEventDispatcher(intent: TransferContract.Intent) = intent {
        when(intent) {
            TransferContract.Intent.OpenDeleteAllUsersDialog -> {
                postSideEffect(TransferContract.SideEffect.OpenDeleteAllUsersDialog)
            }
            TransferContract.Intent.DeleteAll -> {
                repo.deleteAllItems()
                reduce {
                    TransferContract.UIState.SearchedItems(repo.search(""). map { it.toUIData() })
                }
            }
            is TransferContract.Intent.GetCardOwner -> {
                getCardOwner(intent.pan).launch(viewModelScope) { result ->
                    result.onSuccess { response ->
                        intent{
                            reduce {
                                TransferContract.UIState.RemoteSearchItem(
                                    UserCardData(
                                        0,
                                        intent.pan,
                                        response.user
                                    )
                                )
                            }
                        }
                    }
                    result.onFailure { error ->
                        intent{
                            reduce {
                                TransferContract.UIState.ErrorMes(error.message.toString())
                            }
                        }
                    }
                }
            }
            is TransferContract.Intent.SearchFromLocal -> {
                reduce {
                    TransferContract.UIState.SearchedItems(repo.search(intent.pan). map { it.toUIData() })
                }
            }
            is TransferContract.Intent.NavigateTransferCardScreen -> {
                directions.navigateTransferCard(intent.userCardData)
            }
            TransferContract.Intent.LoadMyCardsData -> {
                MyDataLoader.listStateFlow.launch(viewModelScope) {
                    intent{ reduce { TransferContract.UIState.MyCardsData(it) } }
                }
            }
            TransferContract.Intent.OpenMyCardsDialog -> {
                postSideEffect(TransferContract.SideEffect.OpenMyCardsDialog)
            }
            TransferContract.Intent.NavigateAddCardScreen -> {
                directions.navigateAddCardScreen()
            }
            is TransferContract.Intent.NavigateTransferMyCardScreen -> {
                MyDataLoader.listStateFlow.launch(viewModelScope) {
                    intent { directions.navigateTransferMyCardScreen(it, intent.data) }
                }
            }
        }
    }

}