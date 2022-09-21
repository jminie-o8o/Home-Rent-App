package com.example.home_rent_app.data.api

import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.data.dto.MapResponseDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MapApi {

    @GET("map-geocode/v2/geocode")
    suspend fun getAddress(
        @Header("X-NCP-APIGW-API-KEY-ID") id: String = BuildConfig.naverMapId,
        @Header("X-NCP-APIGW-API-KEY") key: String = BuildConfig.naverMapKey,
        @Query("query") name: String
    ): MapResponseDTO
}
