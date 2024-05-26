package com.example.mobilebank.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room.databaseBuilder
import com.example.mobilebank.data.local.dao.CardDao
import com.example.mobilebank.data.local.dao.LastTransferUserDao
import com.example.mobilebank.data.local.db.AppDB
import com.example.mobilebank.data.local.pref.MyPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {
    @[Provides Singleton]
    fun providePreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("myPref", Context.MODE_PRIVATE)


    @[Provides Singleton]
    fun provideShared(sharedPreferences: SharedPreferences): MyPref = MyPref(sharedPreferences)

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): AppDB =
         databaseBuilder(context, AppDB::class.java, "app_db").build()

    @[Provides Singleton]
    fun provideCardDao(appDB: AppDB): CardDao =
        appDB.cardDao()

    @[Provides Singleton]
    fun provideLastTransferUserDao(appDB: AppDB): LastTransferUserDao =
        appDB.usersDao()
}