package com.example.mobilebank.data.model

import android.os.Parcelable
import java.io.Serializable


data class CardData(
    val id: String,
    val name: String,
    val amount: Long,
    val owner: String,
    val pan: String,
    val expiredYear: Int,
    val expiredMonth: Int,
    val themeType: Int,
    val isVisible: Boolean,
) : Serializable
