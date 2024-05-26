package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.LoginVerifyRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginVerifyUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(token: String, code: String) =
        repo.loginVerify(LoginVerifyRequest(token, code))
}