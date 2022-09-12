package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRentHomeApi {

    @GET("houses/rent")
    suspend fun searchRentHome(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("availableOnly") availableOnly: Boolean,
        @Query("sortedBy") sortedBy: String,
        @Query("keyword") searchAddress: String
    ): RoomSearchResultDTO
}
