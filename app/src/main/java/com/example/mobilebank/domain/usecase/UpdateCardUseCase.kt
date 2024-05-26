package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.UpdateCardRequest
import com.example.mobilebank.domain.repositories.CardRepository
import javax.inject.Inject

class UpdateCardUseCase @Inject constructor(
    private val repo: CardRepository
) {
    operator fun invoke(id: String, name: String, themeType: Int, isVisible: Boolean) =
        repo.updateCard(UpdateCardRequest(id.toInt(), name, themeType, isVisible.toString()))
}