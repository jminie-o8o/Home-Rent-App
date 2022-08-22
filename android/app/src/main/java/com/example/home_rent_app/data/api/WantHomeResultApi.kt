package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.WantHomeResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WantHomeResultApi {

    @GET("houses/wanted")
    suspend fun getWantHomeResult(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("keyword") keyword: String,
        @Query("availableOnly") availableOnly: Boolean
    ): WantHomeResultDTO
}
