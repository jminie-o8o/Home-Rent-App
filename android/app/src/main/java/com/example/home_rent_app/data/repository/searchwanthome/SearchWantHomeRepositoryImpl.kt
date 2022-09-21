package com.example.home_rent_app.data.repository.searchwanthome

import androidx.paging.PagingData
import com.example.home_rent_app.data.datasource.searchwanthome.SearchWantHomeDataSource
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.data.session.UserSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchWantHomeRepositoryImpl @Inject constructor(
    private val searchWantHomeDataSource: SearchWantHomeDataSource,
    private val userSession: UserSession
) : SearchWantHomeRepository {
    override suspend fun getResult(
        wantHomeResultRequest: WantHomeResultRequest
    ): Flow<PagingData<WantedArticle>> {
        return searchWantHomeDataSource.getResult(wantHomeResultRequest)
    }

    override suspend fun addBookmark(
        articleId: Int
    ): AddOrDeleteBookMarkResponseDTO {
        return searchWantHomeDataSource.addBookmark(BookmarkRequest(userSession.userId ?: 0, articleId))
    }

    override suspend fun deleteBookmark(
        articleId: Int
    ): AddOrDeleteBookMarkResponseDTO {
        return searchWantHomeDataSource.deleteBookmark(BookmarkRequest(userSession.userId ?: 0, articleId))
    }
}
