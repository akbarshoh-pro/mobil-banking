package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateCardResponse (
    @SerializedName("message")
    val mes: String
)