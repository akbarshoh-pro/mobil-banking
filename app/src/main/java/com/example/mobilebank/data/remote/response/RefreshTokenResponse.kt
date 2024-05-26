package com.example.mobilebank.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("refresh-token")
    val refreshToken: String,
    @SerializedName("access-token")
    val accessToken: String
)

/*
* {
    "refresh-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTQzLjE5OC40OC4yMjI6ODQvdjEvbW9iaWxlLWJhbmsvYXV0aCIsImlzcyI6Imh0dHA6Ly8xNDMuMTk4LjQ4LjIyMjo4NC92MS9tb2JpbGUtYmFuayIsImV4cCI6MTY3MjMxNzM1NywiYWNjb3VudC1waG9uZSI6Iis5OTg5OTM5NDYyODAifQ.2Kds8pcOq-O6WjIb4V9E5uHR2sKY0wgGb7kByyq9R44",
    "access-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTQzLjE5OC40OC4yMjI6ODQvdjEvbW9iaWxlLWJhbmsvYXV0aCIsImFjY291bnQtaWQiOiIyIiwiaXNzIjoiaHR0cDovLzE0My4xOTguNDguMjIyOjg0L3YxL21vYmlsZS1iYW5rIiwiZXhwIjoxNjcxMTA4MDU3fQ.e9QLElvpQ8VjhRtCeY_Uw6wPoDmPhEd54_XUQ4EB7tY"
}
* */