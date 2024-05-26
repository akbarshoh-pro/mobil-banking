package com.example.mobilebank.data.remote.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh-token")
    val refreshToken: String
)

/*
* {
    "refresh-token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMTQzLjE5OC40OC4yMjI6ODQvdjEvbW9iaWxlLWJhbmsvYXV0aCIsImlzcyI6Imh0dHA6Ly8xNDMuMTk4LjQ4LjIyMjo4NC92MS9tb2JpbGUtYmFuayIsImV4cCI6MTY3MjQ5NzIyOSwiYWNjb3VudC1waG9uZSI6Ijk5Mzk0NjI4MCJ9._7nS-1mqdpy5lqwasBtLC9uez0dX7PrFMDb2fNSRDWw"
}
* */