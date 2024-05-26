package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.GetFeeRequest
import com.example.mobilebank.domain.repositories.TransferRepository
import javax.inject.Inject

class GetFeeUseCase @Inject constructor(
    private val repo: TransferRepository
) {
    operator fun invoke(senderId: String, receiver: String, amount: Long) =
        repo.getFee(GetFeeRequest(senderId, receiver, amount))
}