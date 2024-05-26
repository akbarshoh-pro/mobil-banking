package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterResendRequest(
    @SerializedName("token")
    val token: String
)
