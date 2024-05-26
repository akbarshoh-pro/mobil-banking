package com.example.mobilebank.domain.usecase

import com.example.mobilebank.domain.repositories.AuthRepository
import com.example.mobilebank.domain.repositories.CardRepository
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val repo: CardRepository
) {
    operator fun invoke() =
        repo.getAllCards()

}