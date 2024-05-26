package com.example.mobilebank.data.local.pref

import android.content.SharedPreferences
import com.example.mobilebank.utils.getCurrentLanguage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPref @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun userIsRegistered() : Boolean =
        sharedPreferences.getBoolean("userIsRegistered", false)

    fun userRegister() =
        sharedPreferences.edit().putBoolean("userIsRegistered", true).apply()

    fun userUnRegister() =
        sharedPreferences.edit().putBoolean("userIsRegistered", false).apply()

    fun appLanguage() : String =
        sharedPreferences.getString("lang", "uz").toString()

    fun changeLanguage(lang: String) {
        sharedPreferences.edit().putString("lang", lang).apply()
    }

    fun setRefreshToken(token: String) {
        sharedPreferences.edit().putString("refreshToken", token).apply()
    }

    fun getRefreshToken() : String =
        sharedPreferences.getString("refreshToken", "").toString()

    fun setAccessToken(token: String) {
        sharedPreferences.edit().putString("accessToken", token).apply()
    }

    fun getAccessToken() : String =
        sharedPreferences.getString("accessToken", "").toString()

    fun setPin(pin: String) {
        sharedPreferences.edit().putString("pin", pin).apply()
    }

    fun getPin() : String =
        sharedPreferences.getString("pin", "0").toString()

    fun setPhone(phone: String) {
        sharedPreferences.edit().putString("phone", phone).apply()
    }

    fun getPhone() : String =
        sharedPreferences.getString("phone", "").toString()

    fun showBalance() : Boolean =
        sharedPreferences.getBoolean("showBalance", true)

    fun changeShowBalanceState(showBalance: Boolean) {
        sharedPreferences.edit().putBoolean("showBalance", showBalance).apply()
    }

    fun setBiometryUnlock(value: Boolean) {
        sharedPreferences.edit().putBoolean("biometry", value).apply()
    }

    fun getBiometryUnlock() : Boolean =
        sharedPreferences.getBoolean("biometry", false)

}