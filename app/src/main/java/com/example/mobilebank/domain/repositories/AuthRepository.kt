package com.example.mobilebank.domain.repositories

import com.example.mobilebank.data.remote.request.LoginRequest
import com.example.mobilebank.data.remote.request.LoginResendRequest
import com.example.mobilebank.data.remote.request.LoginVerifyRequest
import com.example.mobilebank.data.remote.request.RegisterRequest
import com.example.mobilebank.data.remote.request.RegisterResendRequest
import com.example.mobilebank.data.remote.request.RegisterVerifyRequest
import com.example.mobilebank.data.remote.response.LoginResponse
import com.example.mobilebank.data.remote.response.LoginVerifyResponse
import com.example.mobilebank.data.remote.response.RegisterResponse
import com.example.mobilebank.data.remote.response.RegisterVerifyResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(login: LoginRequest) : Flow<Result<LoginResponse>>
    fun register(register: RegisterRequest) : Flow<Result<RegisterResponse>>
    fun loginVerify(loginVerify: LoginVerifyRequest) : Flow<Result<LoginVerifyResponse>>
    fun registerVerify(registerVerify: RegisterVerifyRequest) : Flow<Result<RegisterVerifyResponse>>
    fun loginResend(loginResend: LoginResendRequest) : Flow<Result<LoginResponse>>
    fun registerResend(registerResend: RegisterResendRequest) : Flow<Result<RegisterResponse>>
}