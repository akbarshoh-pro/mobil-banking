package com.example.mobilebank.domain.repositories

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mobilebank.data.local.entity.CardEntity
import com.example.mobilebank.data.local.entity.LastTransferUserEntity
import com.example.mobilebank.data.model.CardData
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    //room
    fun getAllCardsLocal() : List<CardData>
    fun addCardLocal(card: CardEntity)
    fun deleteCardLocal(card: CardEntity)
    fun updateCardLocal(card: CardEntity)
    fun getAllUsers(): List<LastTransferUserEntity>
    suspend fun deleteAllItems()
    suspend fun addUser(data: LastTransferUserEntity)
    suspend fun updateUser(data: LastTransferUserEntity)
    suspend fun deleteUser(data: LastTransferUserEntity)
    fun search(pan: String): List<LastTransferUserEntity>

    //pref
    fun setRefreshToken(token: String)
    fun getRefreshToken() : String
    fun setAccessToken(token: String)
    fun getAccessToken() : String
    fun setPin(pin: String)
    fun getPin() : String
    fun userRegister()
    fun userUnRegister()
    fun setPhone(phone: String)
    fun getPhone() : String
    fun showBalance() : Boolean
    fun changeShowBalanceState(showBalance: Boolean)
    fun userIsRegistered() : Boolean
    fun setBiometryUnlock(value: Boolean)
    fun getBiometryUnlock() : Boolean
}