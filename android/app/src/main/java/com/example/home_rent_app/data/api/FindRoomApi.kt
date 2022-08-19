package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface FindRoomApi {

    @GET("/houses?page=0&size=100&isCompleted=true&sortedBy=rentFee")
    suspend fun getSearchResult(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("isCompleted") isCompleted : Boolean,
        @Query("sortedBy") sortedBy: String,
        @Query("keyword") searchAddress: String
    ): RoomSearchResultDTO

}