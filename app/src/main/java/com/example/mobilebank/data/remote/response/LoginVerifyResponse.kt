package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginVerifyResponse(
    @SerializedName("refresh-token")
    val refreshToken: String,
    @SerializedName("access-token")
    val accessToken: String
)
