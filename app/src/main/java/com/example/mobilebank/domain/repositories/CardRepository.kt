package com.example.mobilebank.domain.repositories

import com.example.mobilebank.data.remote.request.AddCardRequest
import com.example.mobilebank.data.remote.request.GetCardOwnerRequest
import com.example.mobilebank.data.remote.request.TransferRequest
import com.example.mobilebank.data.remote.request.UpdateCardRequest
import com.example.mobilebank.data.remote.response.AddCardResponse
import com.example.mobilebank.data.remote.response.DeleteCardResponse
import com.example.mobilebank.data.remote.response.GetCardOwnerResponse
import com.example.mobilebank.data.remote.response.GetCardResponse
import com.example.mobilebank.data.remote.response.TransferResponse
import com.example.mobilebank.data.remote.response.UpdateCardResponse
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCards() : Flow<Result<List<GetCardResponse>>>
    fun addCard(request: AddCardRequest) : Flow<Result<AddCardResponse>>
    fun updateCard(request: UpdateCardRequest) : Flow<Result<UpdateCardResponse>>
    fun deleteCard(id: String) : Flow<Result<DeleteCardResponse>>
    fun getCardOwner(request: GetCardOwnerRequest) : Flow<Result<GetCardOwnerResponse>>
}