package com.example.home_rent_app.data.repository.searchwanthome

import androidx.paging.PagingData
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.WantHomeResultRequest
import kotlinx.coroutines.flow.Flow

interface SearchWantHomeRepository {

    suspend fun getResult(wantHomeResultRequest: WantHomeResultRequest): Flow<PagingData<WantedArticle>>

    suspend fun addBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO

    suspend fun deleteBookmark(articleId: Int): AddOrDeleteBookMarkResponseDTO
}
