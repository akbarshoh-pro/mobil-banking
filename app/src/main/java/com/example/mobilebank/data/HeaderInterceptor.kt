package com.example.mobilebank.data

import com.example.mobilebank.data.local.pref.MyPref
import com.example.mobilebank.data.remote.api.AuthApi
import com.example.mobilebank.data.remote.request.RefreshTokenRequest
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Provider

class HeaderInterceptor(
    private val pref: MyPref,
    private val apiProvider: Provider<AuthApi>
) : Interceptor {
        private fun refreshToken(chain: Interceptor.Chain, request: Request): Response {
        val response = apiProvider.get().refreshToken(RefreshTokenRequest(pref.getRefreshToken())).execute()
        if (response.isSuccessful) {
            val access = response.body()?.accessToken
            val refresh = response.body()?.refreshToken
            pref.setRefreshToken(refresh.toString())
            pref.setAccessToken(access.toString())

            val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "Bearer $access")
                .build()
            return chain.proceed(newRequest)
        }
        return chain.proceed(request)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.getAccessToken()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            response.close()
            return refreshToken(chain, request)
        }
        return response
    }
}


