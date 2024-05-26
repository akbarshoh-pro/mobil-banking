package com.example.mobilebank.data.model

import java.io.Serializable

data class SmsRequestType(
    val token: String,
    val requestType: Boolean
) : Serializable
