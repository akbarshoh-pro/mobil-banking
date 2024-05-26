package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class GetCardOwnerRequest(
    @SerializedName("pan")
    val pan: String
)

/*
* {
    "pan": "1234567898765432"
}
* */
