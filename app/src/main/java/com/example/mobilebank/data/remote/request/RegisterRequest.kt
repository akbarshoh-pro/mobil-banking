package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("first-name")
    val firstName: String = "Default",
    @SerializedName("last-name")
    val lastName: String = "Default",
    @SerializedName("born-date")
    val bornDate: String = "969822000000",
    @SerializedName("gender")
    val gender: String = "0"
)

/*
* {
    "phone": "+998993946280",
    "password": "qwerty",
    "first-name": "Muhammadali",
    "last-name": "Rahimberganov",
    "born-date": "969822000000",
    "gender": "0"
}
* */
