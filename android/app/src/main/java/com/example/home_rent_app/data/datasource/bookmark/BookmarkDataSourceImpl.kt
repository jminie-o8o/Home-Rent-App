package com.example.home_rent_app.data.datasource.bookmark

import com.example.home_rent_app.data.api.BookmarkApi
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import javax.inject.Inject

class BookmarkDataSourceImpl @Inject constructor(
    private val api: BookmarkApi
) : BookmarkDataSource {

    override suspend fun getWantBookmark(userId: Int, page: Int): WantBookmarkResponseDTO {
        return api.getWantBookmarkResult(userId, page)
    }

    override suspend fun getGiveBookmark(userId: Int, page: Int): RoomSearchResultDTO {
        return api.getGiveBookmarkResult(userId, page)
    }

    override suspend fun addRentHomeBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO {
        return api.addRentHomeBookmark(bookmarkRequest)
    }

    override suspend fun addWantHomeBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO {
        return api.addWantHomeBookmark(bookmarkRequest)
    }

    override suspend fun deleteRentBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO {
        return api.deleteWantBookmark(bookmarkRequest)
    }

    override suspend fun deleteWantBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO {
        return api.deleteWantBookmark(bookmarkRequest)
    }
}
