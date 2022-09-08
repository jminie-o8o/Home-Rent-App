package com.example.home_rent_app.data.repository.renthome

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO

interface RentHomeRepository {

    suspend fun addRentHome(request: AddRentHomeRequest): AddWantHomeResponseDTO
}
