package com.example.home_rent_app.ui.bookmark

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.home_rent_app.data.api.BookmarkApi
import com.example.home_rent_app.data.dto.WantedArticleBookmark
import com.example.home_rent_app.util.UserSession

class BookmarkPagingSource(
    private val api: BookmarkApi,
    private val userSession: UserSession
) : PagingSource<Int, WantedArticleBookmark>() {
    override fun getRefreshKey(state: PagingState<Int, WantedArticleBookmark>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WantedArticleBookmark> {
        val start = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getWantBookmarkResult(
                userId = userSession.userId ?: 0,
                start,
                PAGE_SIZE
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
