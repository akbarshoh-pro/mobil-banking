package com.example.mobilebank.di

import com.example.mobilebank.domain.repositories.AuthRepository
import com.example.mobilebank.domain.repositories.CardRepository
import com.example.mobilebank.domain.repositories.HomeRepository
import com.example.mobilebank.domain.repositories.LocalRepository
import com.example.mobilebank.domain.repositories.TransferRepository
import com.example.mobilebank.domain.repositories.imp.AuthRepositoryImp
import com.example.mobilebank.domain.repositories.imp.CardRepositoryImp
import com.example.mobilebank.domain.repositories.imp.HomeRepositoryImp
import com.example.mobilebank.domain.repositories.imp.LocalRepositoryImp
import com.example.mobilebank.domain.repositories.imp.TransferRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepo(imp: LocalRepositoryImp) : LocalRepository

    @Binds
    fun bindAuthRepository(imp: AuthRepositoryImp) : AuthRepository

    @Binds
    fun bindTransferRepository(imp: TransferRepositoryImp) : TransferRepository

    @Binds
    fun bindCardRepository(imp: CardRepositoryImp) : CardRepository

    @Binds
    fun bindHomeRepository(imp: HomeRepositoryImp) : HomeRepository

}