package com.example.mobilebank.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilebank.data.local.dao.CardDao
import com.example.mobilebank.data.local.dao.LastTransferUserDao
import com.example.mobilebank.data.local.entity.CardEntity
import com.example.mobilebank.data.local.entity.LastTransferUserEntity

@Database(entities = [CardEntity::class, LastTransferUserEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun cardDao() : CardDao

    abstract fun usersDao() : LastTransferUserDao
}