package com.example.mobilebank.domain.usecase

import com.example.mobilebank.data.remote.request.UpdateDataRequest
import com.example.mobilebank.domain.repositories.HomeRepository
import javax.inject.Inject

class UpdateDataUseCase @Inject constructor(
    private val repo: HomeRepository
) {
    operator fun invoke(firstName: String, lastName: String, bornDate: String) =
        repo.updateData(UpdateDataRequest(bornDate, firstName, "0", lastName))
}