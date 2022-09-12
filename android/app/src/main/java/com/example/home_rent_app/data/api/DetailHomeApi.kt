package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.DetailHomeDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailHomeApi {

    @GET("/houses/rent/{houseId}")
    suspend fun getDetailHomeDTO(
        @Path("houseId") id: Int
    ): DetailHomeDTO
}
