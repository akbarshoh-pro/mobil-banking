package com.example.mobilebank.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mobilebank.data.HeaderInterceptor
import com.example.mobilebank.data.local.pref.MyPref
import com.example.mobilebank.data.remote.api.AuthApi
import com.example.mobilebank.data.remote.api.CardApi
import com.example.mobilebank.data.remote.api.HomeApi
import com.example.mobilebank.data.remote.api.TransferApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InternetModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttp(@ApplicationContext context: Context, pref: MyPref, apiProvider: Provider<AuthApi>): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(context))
            .addInterceptor(HeaderInterceptor(pref, apiProvider))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient : OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("http://195.158.16.140/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCardApi(retrofit: Retrofit): CardApi = retrofit.create(CardApi::class.java)

    @Provides
    @Singleton
    fun provideTransferApi(retrofit: Retrofit): TransferApi = retrofit.create(TransferApi::class.java)

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

}
