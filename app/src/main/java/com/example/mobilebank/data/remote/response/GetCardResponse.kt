package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetCardResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("pan")
    val pan: String,
    @SerializedName("expired-year")
    val expiredYear: Int,
    @SerializedName("expired-month")
    val expiredMonth: Int,
    @SerializedName("theme-type")
    val themeType: Int,
    @SerializedName("is-visible")
    val isVisible: Boolean,
)

/*
* [
    {
        "id": 21,
        "name": "Personal",
        "amount": 20000000,
        "owner": "Muhammadali",
        "pan": "5420",
        "expired-year": 2023,
        "expired-month": 9,
        "theme-type": 4,
        "is-visible": true
    }
]
* */
