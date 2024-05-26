package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterVerifyResponse(
    @SerializedName("refresh-token")
    val refreshToken: String,
    @SerializedName("access-token")
    val accessToken: String
)

/*
* {
    "refresh-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTQzLjE5OC40OC4yMjI6ODQvdjEvbW9iaWxlLWJhbmsvYXV0aCIsImlzcyI6Imh0dHA6Ly8xNDMuMTk4LjQ4LjIyMjo4NC92MS9tb2JpbGUtYmFuayIsImV4cCI6MTY3MjMxNzI1NywiYWNjb3VudC1waG9uZSI6Iis5OTg5OTM5NDYyODAifQ.splWCb9X07H1I1rBbzeyItg3-ZixqBSsj2L-u5a-unk",
    "access-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTQzLjE5OC40OC4yMjI6ODQvdjEvbW9iaWxlLWJhbmsvYXV0aCIsImFjY291bnQtaWQiOiIyIiwiaXNzIjoiaHR0cDovLzE0My4xOTguNDguMjIyOjg0L3YxL21vYmlsZS1iYW5rIiwiZXhwIjoxNjcxMTA3OTU3fQ.ui6zThIn1A7vPc5NPSPbB8x1r14Nae5K83OmX4Yw2Ww"
}
* */
