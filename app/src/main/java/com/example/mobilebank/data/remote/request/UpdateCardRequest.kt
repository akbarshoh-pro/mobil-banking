package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateCardRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("theme-type")
    val themeType: Int,
    @SerializedName("is-visible")
    val isVisible: String
)

/*
* {
    "id": 3,
    "name": "Basic",
    "theme-type": 3,
    "is-visible": "false"
}
* */