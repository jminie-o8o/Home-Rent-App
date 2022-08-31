package com.example.home_rent_app.data.repository.bookmark

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.home_rent_app.data.api.BookmarkApi
import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.ui.bookmark.BookmarkPagingSource
import com.example.home_rent_app.util.UserSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val api: BookmarkApi,
    private val userSession: UserSession
) : BookmarkRepository {

    override suspend fun getWantBookmark(userId: Int, page: Int): MutableList<WantedArticleBookmark> {
        return api.getResult(userId, page).wantedArticles.toMutableList()
    }

    override suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO {
        return api.deleteBookmark(bookmarkRequest)
    }
}
