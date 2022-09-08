package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.datasource.bookmark.BookmarkDataSource
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantBookmarkResponseDTO
import com.example.home_rent_app.data.dto.toRoomSearchResult
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.data.session.UserSession
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val userSession: UserSession,
    private val bookmarkDataSource: BookmarkDataSource
) : BookmarkRepository {

    override suspend fun getWantBookmark(page: Int):
        WantBookmarkResponseDTO {
        return bookmarkDataSource.getWantBookmark(userSession.userId ?: 0, page)
    }

    override suspend fun getGiveBookmark(page: Int):
        RoomSearchResult {
        return bookmarkDataSource.getGiveBookmark(userSession.userId ?: 0, page)
            .toRoomSearchResult()
    }

    override suspend fun addRentHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO {
        return bookmarkDataSource.addRentHomeBookmark(
            BookmarkRequest(
                userSession.userId ?: 0,
                articleId
            )
        )
    }

    override suspend fun addWantHomeBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO {
        return bookmarkDataSource.addWantHomeBookmark(
            BookmarkRequest(
                userSession.userId ?: 0,
                articleId
            )
        )
    }

    override suspend fun deleteRentHomeBookmark(articleId: Int):
        AddOrDeleteBookMarkResponseDTO {
        return bookmarkDataSource.deleteRentBookmark(
            BookmarkRequest(
                userSession.userId ?: 0,
                articleId
            )
        )
    }

    override suspend fun deleteWantHomeBookmark(articleId: Int):
        AddOrDeleteBookMarkResponseDTO {
        return bookmarkDataSource.deleteWantBookmark(
            BookmarkRequest(
                userSession.userId ?: 0,
                articleId
            )
        )
    }
}
