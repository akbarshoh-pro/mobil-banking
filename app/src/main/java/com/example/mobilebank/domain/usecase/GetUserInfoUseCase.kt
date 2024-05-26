package com.example.mobilebank.domain.usecase

import com.example.mobilebank.domain.repositories.HomeRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val repo: HomeRepository
) {
    operator fun invoke() =
        repo.getUserInfo()
}