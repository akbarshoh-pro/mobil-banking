package com.example.mobilebank.domain.repositories

import androidx.paging.PagingData
import com.example.mobilebank.data.remote.request.GetFeeRequest
import com.example.mobilebank.data.remote.request.TransferRequest
import com.example.mobilebank.data.remote.request.TransferResendRequest
import com.example.mobilebank.data.remote.request.TransferVerifyRequest
import com.example.mobilebank.data.remote.response.Child
import com.example.mobilebank.data.remote.response.GetFeeResponse
import com.example.mobilebank.data.remote.response.GetHistoryResponse
import com.example.mobilebank.data.remote.response.TransferResendResponse
import com.example.mobilebank.data.remote.response.TransferResponse
import com.example.mobilebank.data.remote.response.TransferVerifyResponse
import kotlinx.coroutines.flow.Flow

interface TransferRepository {
    fun getFee(request: GetFeeRequest) : Flow<Result<GetFeeResponse>>

    fun transferMoney(request: TransferRequest) : Flow<Result<TransferResponse>>

    fun transferVerify(request: TransferVerifyRequest) : Flow<Result<TransferVerifyResponse>>

    fun transferResend(request: TransferResendRequest) : Flow<Result<TransferResendResponse>>

    fun getHistory() : Flow<PagingData<Child>>
}