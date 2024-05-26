package com.example.mobilebank.domain.repositories.imp

import com.example.mobilebank.data.remote.api.AuthApi
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
import com.example.mobilebank.domain.repositories.AuthRepository
import com.example.mobilebank.utils.emitWith
import com.example.mobilebank.utils.safetyFlow
import com.example.mobilebank.utils.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override fun login(login: LoginRequest): Flow<Result<LoginResponse>> = safetyFlow {
        api.login(login)
            .toResult()
            .emitWith()
    }

    override fun register(register: RegisterRequest): Flow<Result<RegisterResponse>> = safetyFlow {
        api.register(register)
            .toResult()
            .emitWith()
    }

    override fun loginVerify(loginVerify: LoginVerifyRequest): Flow<Result<LoginVerifyResponse>> = safetyFlow {
        api.loginVerify(loginVerify)
            .toResult()
            .emitWith()
    }

    override fun registerVerify(registerVerify: RegisterVerifyRequest): Flow<Result<RegisterVerifyResponse>> = safetyFlow {
        api.registerVerify(registerVerify)
            .toResult()
            .emitWith()
    }

    override fun loginResend(loginResend: LoginResendRequest): Flow<Result<LoginResponse>> = flow {
        api.loginResend(loginResend)
            .toResult()
            .emitWith()
    }

    override fun registerResend(registerResend: RegisterResendRequest): Flow<Result<RegisterResponse>> = safetyFlow {
        api.registerResend(registerResend)
            .toResult()
            .emitWith()
    }
}