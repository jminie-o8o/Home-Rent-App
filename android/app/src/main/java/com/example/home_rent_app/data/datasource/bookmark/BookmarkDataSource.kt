package com.example.home_rent_app.data.datasource.bookmark

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest

interface BookmarkDataSource {

    suspend fun getWantBookmark(userId: Int, page: Int): WantBookmarkResponseDTO

    suspend fun getGiveBookmark(userId: Int, page: Int): RoomSearchResultDTO

    suspend fun addRentHomeBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun addWantHomeBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteRentBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteWantBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO
}
