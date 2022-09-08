package com.example.home_rent_app.data.repository.wanthomeresult

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.home_rent_app.data.api.SearchWantHomeApi
import com.example.home_rent_app.data.dto.AddOrDeleteBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.ui.searchwanthome.WantHomePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WantHomeResultRepositoryImpl @Inject constructor(private val api: SearchWantHomeApi) :
    WantHomeResultRepository {
    override suspend fun getResult(
        wantHomeResultRequest: WantHomeResultRequest
    ): Flow<PagingData<WantedArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { WantHomePagingSource(api, wantHomeResultRequest) }
        ).flow
    }

    override suspend fun addBookmark(
        bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO {
        return api.addBookmark(bookmarkRequest)
    }

    override suspend fun deleteBookmark(
        bookmarkRequest: BookmarkRequest
    ): AddOrDeleteBookMarkResponseDTO {
        return api.deleteBookmark(bookmarkRequest)
    }
}
