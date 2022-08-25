package com.example.home_rent_app.data.repository.wanthomeresult

import com.example.home_rent_app.data.api.WantHomeResultApi
import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WantHomeResultRepositoryImpl @Inject constructor(private val api: WantHomeResultApi) :
    WantHomeResultRepository {
    override suspend fun getResult(wantHomeResultRequest: WantHomeResultRequest): Flow<List<WantedArticle>> {
        return flow {
            emit(
                api.getWantHomeResult(
                    wantHomeResultRequest.page,
                    wantHomeResultRequest.size,
                    wantHomeResultRequest.keyword,
                    wantHomeResultRequest.availableOnly
                ).wantedArticles
            )
        }
    }

    override suspend fun addBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO {
        return api.addBookmark(bookmarkRequest)
    }

    override suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO {
        return api.deleteBookmark(bookmarkRequest)
    }
}
