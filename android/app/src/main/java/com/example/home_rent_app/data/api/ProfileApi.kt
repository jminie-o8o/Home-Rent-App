package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ProfileApi {

    @DELETE("houses/{houseId}")
    suspend fun deleteItem(
        @Path("houseId") id: Int
    ): DeleteHomeResponseDTO
}
