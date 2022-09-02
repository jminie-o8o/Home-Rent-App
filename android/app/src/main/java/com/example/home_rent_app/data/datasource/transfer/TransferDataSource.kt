package com.example.home_rent_app.data.datasource.transfer

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.ImageUrlDTO
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TransferDataSource {

    fun getImageUrl(list : List<MultipartBody.Part>): Flow<ImageUrlDTO>

    suspend fun addRentHome(request: AddRentHomeRequest): AddWantHomeResponseDTO

}