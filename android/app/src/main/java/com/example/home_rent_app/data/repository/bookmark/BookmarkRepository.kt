package com.example.home_rent_app.data.repository.bookmark

import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest

interface BookmarkRepository {

    suspend fun getWantBookmark(userId: Int, page: Int): MutableList<WantedArticleBookmark>

    suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO
}
