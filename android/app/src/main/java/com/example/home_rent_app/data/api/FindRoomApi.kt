package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface FindRoomApi {

    @GET("houses")
    suspend fun getSearchResult(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("isCompleted") isCompleted : Boolean,
        @Query("sortedBy") sortedBy: String,
        @Query("keyword") searchAddress: String
    ): RoomSearchResultDTO

}