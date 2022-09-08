package com.example.home_rent_app.data.datasource.renthome

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO

interface RentHomeDataSource {

    suspend fun addRentHome(request: AddRentHomeRequest): AddWantHomeResponseDTO
}
