package com.example.home_rent_app.data.repository.wanthomeresult

import androidx.paging.PagingData
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import kotlinx.coroutines.flow.Flow

interface WantHomeResultRepository {

    suspend fun getResult(wantHomeResultRequest: WantHomeResultRequest): Flow<PagingData<WantedArticle>>

    suspend fun addBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): AddOrDeleteBookMarkResponseDTO
}
