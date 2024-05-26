package com.example.mobilebank.data

import com.example.mobilebank.data.local.entity.CardEntity
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.data.remote.response.GetCardResponse

object MyMapper {
    fun GetCardResponse.toUIData() : CardData =
        CardData(id, name, amount, owner, pan, expiredYear, expiredMonth, themeType, isVisible)

    fun CardEntity.toUIData() : CardData =
        CardData(id.toString(), name, amount, owner, pan, expiredYear, expiredMonth, themeType, isVisible)
}