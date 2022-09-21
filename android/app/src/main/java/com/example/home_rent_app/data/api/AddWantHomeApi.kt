package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.AddWantHomeResponseDTO
import com.example.home_rent_app.data.dto.WantHomeDetailResponseDTO
import com.example.home_rent_app.data.model.AddWantHomeRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AddWantHomeApi {

    @POST("houses/wanted")
    suspend fun addWantHome(
        @Body addWantHomeRequest: AddWantHomeRequest
    ): AddWantHomeResponseDTO

    @GET("houses/wanted/{id}")
    suspend fun getWantHome(
        @Path("id") itemId: Int
    ): WantHomeDetailResponseDTO
}
