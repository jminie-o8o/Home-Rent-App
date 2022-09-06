package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.GiveBookmarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest

interface BookmarkRepository {

    suspend fun getWantBookmark(userId: Int, page: Int): WantBookmarkResponseDTO

    suspend fun getGiveBookmark(userId: Int, page: Int): GiveBookmarkResponseDTO

    suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO
}
