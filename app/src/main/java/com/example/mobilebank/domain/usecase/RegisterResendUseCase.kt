package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.RegisterResendRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterResendUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(token: String) =
        repo.registerResend(RegisterResendRequest(token))
}