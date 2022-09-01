package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.WantHomeResultDTO
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface WantHomeResultApi {

    @GET("houses/wanted")
    suspend fun getWantHomeResult(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("keyword") keyword: String,
        @Query("availableOnly") availableOnly: Boolean
    ): WantHomeResultDTO

    @POST("houses/wanted/bookmarks")
    suspend fun addBookmark(
        @Body bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO

    @HTTP(method = "DELETE", path = "houses/wanted/bookmarks", hasBody = true)
    suspend fun deleteBookmark(
        @Body bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO

}

