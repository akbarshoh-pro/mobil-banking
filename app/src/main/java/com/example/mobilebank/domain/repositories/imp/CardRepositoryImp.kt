package com.example.mobilebank.domain.repositories.imp

import com.example.mobilebank.data.remote.api.CardApi
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
import com.example.mobilebank.domain.repositories.CardRepository
import com.example.mobilebank.utils.emitWith
import com.example.mobilebank.utils.logger
import com.example.mobilebank.utils.safetyFlow
import com.example.mobilebank.utils.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImp @Inject constructor(
    private val api: CardApi
) : CardRepository {
    override fun getAllCards(): Flow<Result<List<GetCardResponse>>> = safetyFlow {
        api.getAllCards()
            .toResult()
            .emitWith()
    }

    override fun addCard(request: AddCardRequest): Flow<Result<AddCardResponse>> = safetyFlow {
        api.addCard(request)
            .toResult()
            .onSuccess { getAllCards() }
            .emitWith()
    }

    override fun updateCard(request: UpdateCardRequest): Flow<Result<UpdateCardResponse>> = safetyFlow {
        api.updateCard(request)
            .toResult()
            .onSuccess { getAllCards() }
            .emitWith()
    }

    override fun deleteCard(id: String): Flow<Result<DeleteCardResponse>> = safetyFlow {
        api.deleteCard(id)
            .toResult()
            .onSuccess {  }
            .emitWith()
    }

    override fun getCardOwner(request: GetCardOwnerRequest): Flow<Result<GetCardOwnerResponse>> = safetyFlow {
        api.getCardOwner(request)
            .toResult()
            .emitWith()
    }
}