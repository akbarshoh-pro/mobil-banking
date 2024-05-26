package com.example.mobilebank.domain.repositories.imp

import com.example.mobilebank.data.remote.api.HomeApi
import com.example.mobilebank.data.remote.request.UpdateDataRequest
import com.example.mobilebank.data.remote.response.GetUserInfoResponse
import com.example.mobilebank.data.remote.response.UpdateDataResponse
import com.example.mobilebank.domain.repositories.HomeRepository
import com.example.mobilebank.utils.emitWith
import com.example.mobilebank.utils.safetyFlow
import com.example.mobilebank.utils.toResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImp @Inject constructor(
    private val api: HomeApi
) : HomeRepository {
    override fun updateData(request: UpdateDataRequest): Flow<Result<UpdateDataResponse>> = safetyFlow {
        api.updateData(request)
            .toResult()
            .emitWith()
    }

    override fun getUserInfo(): Flow<Result<GetUserInfoResponse>> = safetyFlow {
        api.getUserInfo()
            .toResult()
            .emitWith()
    }

}