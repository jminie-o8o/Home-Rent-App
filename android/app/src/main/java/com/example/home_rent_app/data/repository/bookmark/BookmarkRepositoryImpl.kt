package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.api.BookmarkApi
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.dto.toRoomSearchResult
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.RoomSearchResult
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val api: BookmarkApi
) : BookmarkRepository {

    override suspend fun getWantBookmark(userId: Int, page: Int):
            WantBookmarkResponseDTO {
        return api.getWantBookmarkResult(userId, page)
    }

    override suspend fun getGiveBookmark(userId: Int, page: Int):
            RoomSearchResult {
        return api.getGiveBookmarkResult(userId, page).toRoomSearchResult()
    }

    override suspend fun deleteWantBookmark(bookmarkRequest: BookmarkRequest):
            AddOrDeleteBookMarkResponseDTO {
        return api.deleteWantBookmark(bookmarkRequest)
    }

    override suspend fun deleteGiveBookmark(bookmarkRequest: BookmarkRequest):
            AddOrDeleteBookMarkResponseDTO {
        return api.deleteRentBookmark(bookmarkRequest)
    }
}
