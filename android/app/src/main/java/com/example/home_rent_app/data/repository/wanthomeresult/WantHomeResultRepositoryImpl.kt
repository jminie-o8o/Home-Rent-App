package com.example.home_rent_app.data.repository.wanthomeresult

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.home_rent_app.data.api.RetrofitInstance
import com.example.home_rent_app.data.api.WantHomeResultApi
import com.example.home_rent_app.data.dto.WantHouseBookMarkResponseDTO
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.ui.wanthomeresult.WantHomePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WantHomeResultRepositoryImpl @Inject constructor(private val api: WantHomeResultApi) :
    WantHomeResultRepository {
    override suspend fun getResult(wantHomeResultRequest: WantHomeResultRequest): Flow<PagingData<WantedArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { WantHomePagingSource(api = api, wantHomeResultRequest) }
        ).flow
    }

    override suspend fun addBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO {
        return api.addBookmark(bookmarkRequest)
    }

    override suspend fun deleteBookmark(bookmarkRequest: BookmarkRequest): WantHouseBookMarkResponseDTO {
        return api.deleteBookmark(bookmarkRequest)
    }
}
