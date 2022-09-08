package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.RoomSearchResult

interface BookmarkRepository {

    suspend fun getWantBookmark(page: Int): WantBookmarkResponseDTO

    suspend fun getGiveBookmark(page: Int): RoomSearchResult

    suspend fun addRentHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO

    suspend fun addWantHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteRentHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteWantHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO
}
