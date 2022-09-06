package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.api.BookmarkApi
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.GiveBookmarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val api: BookmarkApi
) : BookmarkRepository {

    override suspend fun getWantBookmark(userId: Int, page: Int):
            WantBookmarkResponseDTO {
        return api.getWantBookmarkResult(userId, page)
    }

    override suspend fun getGiveBookmark(userId: Int, page: Int):
            GiveBookmarkResponseDTO {
        return api.getGiveBookmarkResult(userId, page)
    }

    override suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest):
            AddOrDeleteBookMarkResponseDTO {
        return api.deleteWantBookmark(bookmarkRequest)
    }
}
