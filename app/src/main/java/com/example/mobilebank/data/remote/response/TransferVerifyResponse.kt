package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransferVerifyResponse(
    @SerializedName("message")
    val message: String
)

/*
{
    "message": "Pul muvaffaqiyatli yakunlandi"
}
* */
