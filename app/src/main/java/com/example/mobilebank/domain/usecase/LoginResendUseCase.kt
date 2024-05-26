package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.LoginResendRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginResendUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(token: String) =
        repo.loginResend(LoginResendRequest(token))
}