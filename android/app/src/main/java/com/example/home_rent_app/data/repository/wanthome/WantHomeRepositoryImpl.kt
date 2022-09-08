package com.example.home_rent_app.data.repository.wanthome

import com.example.home_rent_app.data.datasource.wanthome.WantHomeDataSource
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest
import javax.inject.Inject

class WantHomeRepositoryImpl @Inject constructor(
    private val wantHomeDataSource: WantHomeDataSource
) : WantHomeRepository {

    override suspend fun addWantHome(addWantHomeRequest: AddWantHomeRequest): AddWantHomeResponseDTO {
        return wantHomeDataSource.addWantHome(addWantHomeRequest)
    }

    override suspend fun getWantHome(itemId: Int): WantHomeDetailResponseDTO {
        return wantHomeDataSource.getWantHome(itemId)
    }
}
