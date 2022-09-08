package com.example.home_rent_app.ui.searchwanthome

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.home_rent_app.data.api.SearchWantHomeApi
import com.example.home_rent_app.data.dto.WantedArticle
import com.example.home_rent_app.data.model.WantHomeResultRequest
import com.example.home_rent_app.util.logger

class WantHomePagingSource(
    private val api: SearchWantHomeApi,
    private val wantHomeResultRequest: WantHomeResultRequest
) : PagingSource<Int, WantedArticle>() {

    override fun getRefreshKey(state: PagingState<Int, WantedArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WantedArticle> {
        val start = params.key ?: STARTING_PAGE_INDEX
        logger("로그 pagingsource")
        return try {
            val response = api.getWantHomeResult(
                start,
                PAGE_SIZE,
                wantHomeResultRequest.keyword,
                wantHomeResultRequest.availableOnly
            )

            val prevKey = if (start == STARTING_PAGE_INDEX) {
                null
            } else {
                start - STARTING_PAGE_INDEX
            }

            val nextKey = if (!response.hasNext) {
                null
            } else {
                start + params.loadSize
            }
            LoadResult.Page(response.wantedArticles, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 0
        const val PAGE_SIZE = 5
    }
}
