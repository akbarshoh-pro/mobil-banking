package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.TransferResendRequest
import com.example.mobilebank.domain.repositories.TransferRepository
import javax.inject.Inject

class TransferResendUseCase @Inject constructor(
    private val repo: TransferRepository
) {
    operator fun invoke(token: String) =
        repo.transferResend(TransferResendRequest(token))
}