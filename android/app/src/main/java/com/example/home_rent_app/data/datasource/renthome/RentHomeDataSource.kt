package com.example.home_rent_app.data.datasource.renthome

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.ImageUrlDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface RentHomeDataSource {

    suspend fun addRentHome(request: AddRentHomeRequest): AddWantHomeResponseDTO
}
