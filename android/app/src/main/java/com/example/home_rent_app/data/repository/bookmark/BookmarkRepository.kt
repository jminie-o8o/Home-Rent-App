package com.example.home_rent_app.data.repository.bookmark

import androidx.paging.PagingData
import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getWantBookmark(userId: Int): MutableList<WantedArticleBookmark>

    suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO
}
