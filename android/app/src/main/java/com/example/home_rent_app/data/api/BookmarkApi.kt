package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.GiveBookmarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path
import retrofit2.http.Query

interface BookmarkApi {

    @GET("users/{userId}/bookmarks/wanted")
    suspend fun getWantBookmarkResult(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int = 5
    ): WantBookmarkResponseDTO

    @GET("users/{userId}/bookmarks/rent")
    suspend fun getGiveBookmarkResult(
        @Path("userId") userId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int = 5
    ): RoomSearchResultDTO

    @HTTP(method = "DELETE", path = "houses/wanted/bookmarks", hasBody = true)
    suspend fun deleteWantBookmark(
        @Body bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO

    @HTTP(method = "DELETE", path = "houses/rent/bookmarks", hasBody = true)
    suspend fun deleteRentBookmark(
        @Body bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO
}
