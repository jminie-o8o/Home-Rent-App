package com.example.home_rent_app.data.repository.wanthome

import com.example.home_rent_app.data.api.AddWantHomeApi
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest
import javax.inject.Inject

class WantHomeRepositoryImpl @Inject constructor(private val api: AddWantHomeApi) : WantHomeRepository {

    override suspend fun addWantHome(addWantHomeRequest: AddWantHomeRequest): AddWantHomeResponseDTO {
        return api.addWantHome(addWantHomeRequest)
    }
}
