package com.example.mobilebank.utils

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mobilebank.data.MyMapper.toUIData
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.data.model.UserData
import com.example.mobilebank.data.remote.response.Child
import com.example.mobilebank.domain.usecase.GetAllCardsUseCase
import com.example.mobilebank.domain.usecase.GetCardHistoryUseCase
import com.example.mobilebank.domain.usecase.GetUserInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object MyDataLoader {
    private lateinit var getAllCardsUseCase: GetAllCardsUseCase
    private lateinit var getUserInfoUseCase: GetUserInfoUseCase
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    val loadStateFlow = MutableStateFlow(false)
    val listStateFlow = MutableStateFlow(value = listOf<CardData>())
    val userInfoStateFlow = MutableStateFlow(UserData("", "", 0, ""))
    val totalAmountStateFlow = MutableStateFlow(0L)
    val errorStateFlow = MutableStateFlow("")

    fun init(allCards: GetAllCardsUseCase, userInfo: GetUserInfoUseCase) {
        getAllCardsUseCase = allCards
        getUserInfoUseCase = userInfo
    }

    fun loadCardsData() {
        scope.launch {
            loadStateFlow.emit(true)
            getAllCardsUseCase().launch(scope) { result ->
                result.onSuccess { list ->
                var amount = 0L
                list.forEach { amount += it.amount }
                scope.launch {
                    loadStateFlow.emit(false)
                    totalAmountStateFlow.emit(amount)
                    listStateFlow.emit(list.map { it.toUIData() })
                }
            }
                result.onFailure {
                    scope.launch {
                        loadStateFlow.emit(false)
                        errorStateFlow.emit(it.message.toString())
                    }
                }
            }
        }
    }

    fun loadUserData() {
        scope.launch {
            loadStateFlow.emit(true)
            getUserInfoUseCase().launch(scope) { result ->
                result.onSuccess { data ->
                    scope.launch {
                        loadStateFlow.emit(false)
                        userInfoStateFlow.emit(data.toUIData())
                    }
                }
                result.onFailure { error ->
                    scope.launch {
                        loadStateFlow.emit(false)
                        errorStateFlow.emit(error.message.toString())
                    }
                }
            }
        }
    }
}