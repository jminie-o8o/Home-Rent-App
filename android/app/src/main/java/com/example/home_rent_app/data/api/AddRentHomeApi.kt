package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AddRentHomeApi {

    @POST("houses/rent")
    suspend fun addRentHome(
        @Body request: AddRentHomeRequest
    ): AddWantHomeResponseDTO
}
