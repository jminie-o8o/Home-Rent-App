package com.example.home_rent_app.data.datasource.wanthome

import com.example.home_rent_app.data.api.AddWantHomeApi
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest
import javax.inject.Inject

class WantHomeDataSourceImpl @Inject constructor(
    private val api: AddWantHomeApi
) : WantHomeDataSource {

    override suspend fun addWantHome(
        addWantHomeRequest: AddWantHomeRequest
    ): AddWantHomeResponseDTO {
        return api.addWantHome(addWantHomeRequest)
    }

    override suspend fun getWantHome(itemId: Int): WantHomeDetailResponseDTO {
        return api.getWantHome(itemId)
    }
}
