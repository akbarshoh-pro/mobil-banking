package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.AddCardRequest
import com.example.mobilebank.domain.repositories.CardRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repo: CardRepository
) {
    operator fun invoke(
        pan: String,
        expiredYear: String,
        expiredMonth: String,
        name: String = "Personal"
    ) =
        repo.addCard(AddCardRequest(pan, expiredYear, expiredMonth, name))
}