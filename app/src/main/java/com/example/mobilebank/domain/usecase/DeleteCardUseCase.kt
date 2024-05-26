package com.example.mobilebank.domain.usecase

import com.example.mobilebank.domain.repositories.CardRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val repo: CardRepository
) {
    operator fun invoke(id: String) =
        repo.deleteCard(id)
}