package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateDataRequest(
    @SerializedName("born-date")
    val bornDate: String,
    @SerializedName("first-name")
    val firstName: String,
    @SerializedName("gender-type")
    val genderType: String,
    @SerializedName("last-name")
    val lastName: String
)