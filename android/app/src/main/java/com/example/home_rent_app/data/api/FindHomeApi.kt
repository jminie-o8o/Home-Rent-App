package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface FindHomeApi {

    @GET("houses/rent")
    suspend fun getSearchResult(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("availableOnly") availableOnly: Boolean,
        @Query("sortedBy") sortedBy: String,
        @Query("keyword") searchAddress: String
    ): RoomSearchResultDTO
}
