package com.example.mobilebank.data.remote.api

import com.example.mobilebank.data.remote.request.LoginRequest
import com.example.mobilebank.data.remote.request.LoginResendRequest
import com.example.mobilebank.data.remote.request.LoginVerifyRequest
import com.example.mobilebank.data.remote.request.RefreshTokenRequest
import com.example.mobilebank.data.remote.request.RegisterRequest
import com.example.mobilebank.data.remote.request.RegisterResendRequest
import com.example.mobilebank.data.remote.request.RegisterVerifyRequest
import com.example.mobilebank.data.remote.response.LoginResponse
import com.example.mobilebank.data.remote.response.LoginVerifyResponse
import com.example.mobilebank.data.remote.response.RefreshTokenResponse
import com.example.mobilebank.data.remote.response.RegisterResponse
import com.example.mobilebank.data.remote.response.RegisterVerifyResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface  AuthApi {
    @POST("mobile-bank/v1/auth/sign-in")
    suspend fun login(@Body login: LoginRequest) : Response<LoginResponse>
    @POST("mobile-bank/v1/auth/sign-in/verify")
    suspend fun loginVerify(@Body login: LoginVerifyRequest) : Response<LoginVerifyResponse>
    @POST("mobile-bank/v1/auth/sign-in/resend")
    suspend fun loginResend(@Body login: LoginResendRequest) : Response<LoginResponse>

    @POST("mobile-bank/v1/auth/sign-up")
    suspend fun register(@Body register: RegisterRequest) : Response<RegisterResponse>
    @POST("mobile-bank/v1/auth/sign-up/verify")
    suspend fun registerVerify(@Body register: RegisterVerifyRequest) : Response<RegisterVerifyResponse>
    @POST("mobile-bank/v1/auth/sign-up/resend")
    suspend fun registerResend(@Body register: RegisterResendRequest) : Response<RegisterResponse>

    @POST("mobile-bank/v1/auth/update-token")
    fun refreshToken(@Body refreshToken: RefreshTokenRequest) : Call<RefreshTokenResponse>
}