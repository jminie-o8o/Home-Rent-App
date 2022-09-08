package com.example.home_rent_app.data.datasource.findhome

import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import com.example.home_rent_app.data.model.BookmarkRequest
import kotlinx.coroutines.flow.Flow

interface FindHomeDataSource {

    fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly: Boolean,
        sortedBy: String,
        searchAddress: String
    ): Flow<RoomSearchResultDTO>

    suspend fun addBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO
}
