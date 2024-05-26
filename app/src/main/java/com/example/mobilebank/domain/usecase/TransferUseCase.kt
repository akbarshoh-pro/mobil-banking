package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.TransferRequest
import com.example.mobilebank.domain.repositories.TransferRepository
import javax.inject.Inject

class TransferUseCase @Inject constructor(
    private val repo: TransferRepository
) {
    operator fun invoke(senderId: String, receiverPan: String, amount: Long) =
        repo.transferMoney(TransferRequest(senderId = senderId, receiverPan = receiverPan, amount = amount))


}