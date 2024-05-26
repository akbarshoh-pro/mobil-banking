package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetUserInfoResponse(
    @SerializedName("born-date")
    val bornDate: Long,
    @SerializedName("first-name")
    val firstName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("last-name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String
)