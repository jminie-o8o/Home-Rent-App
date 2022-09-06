package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.AddRentHomeRequest
import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.ImageUrlDTO
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TransferApi {
    @Multipart
    @POST("images")
    suspend fun getImageUrl(
        @Part images: List<MultipartBody.Part>
    ): ImageUrlDTO

    @POST("houses/rent")
    suspend fun addRentHome(
        @Body request: AddRentHomeRequest
    ): AddWantHomeResponseDTO
}
