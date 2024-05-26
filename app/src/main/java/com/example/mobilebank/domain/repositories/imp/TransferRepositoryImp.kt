package com.example.mobilebank.domain.repositories.imp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobilebank.data.remote.TestPaginationSource
import com.example.mobilebank.data.remote.api.TransferApi
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
import com.example.mobilebank.domain.repositories.TransferRepository
import com.example.mobilebank.utils.emitWith
import com.example.mobilebank.utils.safetyFlow
import com.example.mobilebank.utils.toResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransferRepositoryImp @Inject constructor(
    private val api: TransferApi
) : TransferRepository {
    override fun getFee(request: GetFeeRequest): Flow<Result<GetFeeResponse>> = safetyFlow {
        api.getFee(request)
            .toResult()
            .emitWith()
    }

    override fun transferMoney(request: TransferRequest): Flow<Result<TransferResponse>> = safetyFlow {
        api.transferMoney(request)
            .toResult()
            .emitWith()
    }

    override fun transferVerify(request: TransferVerifyRequest): Flow<Result<TransferVerifyResponse>> = safetyFlow {
        api.transferVerify(request)
            .toResult()
            .emitWith()
    }

    override fun transferResend(request: TransferResendRequest): Flow<Result<TransferResendResponse>> = safetyFlow {
        api.transferResend(request)
            .toResult()
            .emitWith()
    }

    override fun getHistory(): Flow<PagingData<Child>> = Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { TestPaginationSource(api) }
        ).flow

}