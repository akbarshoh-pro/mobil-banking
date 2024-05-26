package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String
)


/*
* {
    "phone": "+998993946280",
    "password": "qwerty"
}
* */