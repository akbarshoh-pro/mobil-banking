package com.example.mobilebank.data.remote.api

import com.example.mobilebank.data.remote.request.UpdateDataRequest
import com.example.mobilebank.data.remote.response.GetUserInfoResponse
import com.example.mobilebank.data.remote.response.UpdateDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HomeApi {
    @PUT("mobile-bank/v1/home/user-info")
    suspend fun updateData(@Body request: UpdateDataRequest) : Response<UpdateDataResponse>

    @GET("mobile-bank/v1/home/user-info/details")
    suspend fun getUserInfo() : Response<GetUserInfoResponse>
}