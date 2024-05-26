package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.GetCardOwnerRequest
import com.example.mobilebank.domain.repositories.CardRepository
import javax.inject.Inject

class GetCardOwnerUseCase @Inject constructor(
    private val repo: CardRepository
) {
    operator fun invoke(pan: String) =
        repo.getCardOwner(GetCardOwnerRequest(pan))
}