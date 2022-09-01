package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.DeleteHomeResponseDTO
import com.example.home_rent_app.data.dto.GiveHomeProfileDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApi {

    @GET("users/{userId}/articles/rent")
    suspend fun getGiveHomeProfileResult(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int = 5
    ): GiveHomeProfileDTO

    @DELETE("houses/{houseId}")
    suspend fun deleteItem(
        @Path("houseId") id: Int
    ): DeleteHomeResponseDTO
}
