package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.RoomSearchResult

interface BookmarkRepository {

    suspend fun getWantBookmark(userId: Int, page: Int): WantBookmarkResponseDTO

    suspend fun getGiveBookmark(userId: Int, page: Int): RoomSearchResult

    suspend fun deleteWantBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteGiveBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO
}
