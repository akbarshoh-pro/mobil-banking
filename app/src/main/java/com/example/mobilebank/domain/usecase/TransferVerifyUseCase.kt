package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.TransferVerifyRequest
import com.example.mobilebank.domain.repositories.TransferRepository
import javax.inject.Inject

class TransferVerifyUseCase @Inject constructor(
    private val repo: TransferRepository
) {
    operator fun invoke(token: String, code: String) =
        repo.transferVerify(TransferVerifyRequest(token, code))
}