package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.RegisterVerifyRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterVerifyUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(token: String, code: String) =
        repo.registerVerify(RegisterVerifyRequest(token, code))
}