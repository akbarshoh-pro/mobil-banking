package com.example.mobilebank.data.remote.api

import com.example.mobilebank.data.remote.request.AddCardRequest
import com.example.mobilebank.data.remote.request.GetCardOwnerRequest
import com.example.mobilebank.data.remote.request.TransferRequest
import com.example.mobilebank.data.remote.request.UpdateCardRequest
import com.example.mobilebank.data.remote.response.AddCardResponse
import com.example.mobilebank.data.remote.response.DeleteCardResponse
import com.example.mobilebank.data.remote.response.GetCardOwnerResponse
import com.example.mobilebank.data.remote.response.GetCardResponse
import com.example.mobilebank.data.remote.response.TransferResponse
import com.example.mobilebank.data.remote.response.UpdateCardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CardApi {
    @GET("mobile-bank/v1/card")
    suspend fun getAllCards() : Response<List<GetCardResponse>>

    @DELETE("mobile-bank/v1/card/{id}")
    suspend fun deleteCard(@Path("id") id: String) : Response<DeleteCardResponse>

    @POST("mobile-bank/v1/card")
    suspend fun addCard(@Body request: AddCardRequest) : Response<AddCardResponse>

    @PUT("mobile-bank/v1/card")
    suspend fun updateCard(@Body request: UpdateCardRequest) : Response<UpdateCardResponse>

    @POST("mobile-bank/v1/transfer/card-owner")
    suspend fun getCardOwner(@Body request: GetCardOwnerRequest) : Response<GetCardOwnerResponse>

}