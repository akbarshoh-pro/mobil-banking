package com.example.mobilebank.domain.repositories

import com.example.mobilebank.data.remote.request.UpdateDataRequest
import com.example.mobilebank.data.remote.response.GetUserInfoResponse
import com.example.mobilebank.data.remote.response.UpdateDataResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun updateData(request: UpdateDataRequest) : Flow<Result<UpdateDataResponse>>

    fun getUserInfo() : Flow<Result<GetUserInfoResponse>>
}