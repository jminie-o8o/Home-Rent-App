package com.example.home_rent_app.data.repository.wanthome

import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest

interface WantHomeRepository {

    suspend fun addWantHome(addWantHomeRequest: AddWantHomeRequest): AddWantHomeResponseDTO

    suspend fun getWantHome(itemId: Int): WantHomeDetailResponseDTO
}
