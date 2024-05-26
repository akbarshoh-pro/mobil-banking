package com.example.mobilebank.domain.usecase

import com.example.mobilebank.domain.repositories.TransferRepository
import javax.inject.Inject

class GetCardHistoryUseCase @Inject constructor(
    private val repo: TransferRepository
) {
    operator fun invoke() = repo.getHistory()
}