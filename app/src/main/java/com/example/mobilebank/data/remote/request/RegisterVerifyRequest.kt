package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterVerifyRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("code")
    val code: String
)
