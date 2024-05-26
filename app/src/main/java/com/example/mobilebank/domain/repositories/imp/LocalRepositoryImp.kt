package com.example.mobilebank.domain.repositories.imp

import com.example.mobilebank.data.MyMapper.toUIData
import com.example.mobilebank.data.local.dao.CardDao
import com.example.mobilebank.data.local.dao.LastTransferUserDao
import com.example.mobilebank.data.local.entity.CardEntity
import com.example.mobilebank.data.local.entity.LastTransferUserEntity
import com.example.mobilebank.data.local.pref.MyPref
import com.example.mobilebank.data.model.CardData
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.utils.safetyFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImp @Inject constructor(
    private val pref: MyPref,
    private val cardDao: CardDao,
    private val usersDao: LastTransferUserDao
) : LocalRepository {

    //room
    override fun getAllCardsLocal(): List<CardData> =
        cardDao.getAllCards().map { it.toUIData() }

    override fun addCardLocal(card: CardEntity) =
        cardDao.addCard(card)

    override fun deleteCardLocal(card: CardEntity) =
        cardDao.deleteCard(card)

    override fun updateCardLocal(card: CardEntity) =
        cardDao.updateCard(card)

    override fun getAllUsers(): List<LastTransferUserEntity> = usersDao.getAllUsers()
    override suspend fun deleteAllItems() {
        withContext(Dispatchers.IO) {
            usersDao.deleteAllItems()
        }
    }

    override suspend fun addUser(data: LastTransferUserEntity) {
        withContext(Dispatchers.IO) {
            usersDao.addUser(data)
        }
    }

    override suspend fun updateUser(data: LastTransferUserEntity) {
        withContext(Dispatchers.IO) {
            usersDao.updateUser(data)
        }
    }

    override suspend fun deleteUser(data: LastTransferUserEntity) {
        withContext(Dispatchers.IO) {
            usersDao.deleteUser(data)
        }
    }

    override fun search(pan: String) = usersDao.search(pan)

    //pref
    override fun setRefreshToken(token: String) =
        pref.setRefreshToken(token)

    override fun getRefreshToken(): String =
        pref.getRefreshToken()

    override fun setAccessToken(token: String) =
        pref.setAccessToken(token)

    override fun getAccessToken(): String =
        pref.getAccessToken()


    override fun setPin(pin: String) =
        pref.setPin(pin)

    override fun getPin(): String =
        pref.getPin()

    override fun userRegister() =
        pref.userRegister()

    override fun userUnRegister() =
        pref.userUnRegister()

    override fun setPhone(phone: String) =
        pref.setPhone(phone)

    override fun getPhone(): String =
        pref.getPhone()

    override fun showBalance(): Boolean =
        pref.showBalance()

    override fun changeShowBalanceState(showBalance: Boolean) =
        pref.changeShowBalanceState(showBalance)

    override fun userIsRegistered() : Boolean =
        pref.userIsRegistered()

    override fun setBiometryUnlock(value: Boolean) =
        pref.setBiometryUnlock(value)

    override fun getBiometryUnlock(): Boolean =
        pref.getBiometryUnlock()
}