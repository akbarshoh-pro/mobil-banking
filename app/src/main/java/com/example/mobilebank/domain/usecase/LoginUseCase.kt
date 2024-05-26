package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.api.AuthApi
import com.example.mobilebank.data.remote.request.LoginRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(phone: String) =
        repo.login(LoginRequest(phone, "qwerty"))

}