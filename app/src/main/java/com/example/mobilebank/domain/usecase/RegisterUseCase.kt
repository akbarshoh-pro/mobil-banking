package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.RegisterRequest
import com.example.mobilebank.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(phone: String) =
        repo.register(RegisterRequest(phone, "qwerty"))

}