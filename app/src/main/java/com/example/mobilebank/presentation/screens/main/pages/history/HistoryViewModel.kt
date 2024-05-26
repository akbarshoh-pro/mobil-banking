package com.example.mobilebank.presentation.screens.main.pages.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobilebank.data.remote.response.Child
import com.example.mobilebank.domain.usecase.GetCardHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistory: GetCardHistoryUseCase
) : ViewModel(), HistoryContract.ViewModel {
    override val container = container<HistoryContract.UIState, HistoryContract.SideEffect>(HistoryContract.UIState())
    override fun onEventDispatcher(intent: HistoryContract.Intent) = intent {
        when(intent) {
            HistoryContract.Intent.GetHistory -> {}
        }
    }

    override fun getCardHistory(): Flow<PagingData<Child>> = getHistory().cachedIn(viewModelScope)

}