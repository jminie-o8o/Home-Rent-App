package com.example.home_rent_app.data.repository.wanthomeresult

import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.AddBookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import kotlinx.coroutines.flow.Flow

interface WantHomeResultRepository {

    suspend fun getResult(wantHomeResultRequest: WantHomeResultRequest): Flow<List<WantedArticle>>

    suspend fun addBookmark(addBookmarkRequest: AddBookmarkRequest): WantHouseBookMarkResponseDTO
}
