package com.example.mobilebank.data.model

import java.io.Serializable

data class MarkerData(
    val lat: Double,
    val lng: Double,
    val location: String,
    val phone: String,
) : Serializable
