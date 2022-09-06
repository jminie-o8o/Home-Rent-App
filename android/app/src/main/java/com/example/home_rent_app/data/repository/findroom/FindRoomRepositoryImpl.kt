package com.example.home_rent_app.data.repository.findroom

import com.example.home_rent_app.data.datasource.findroom.FindRoomDataSource
import com.example.home_rent_app.data.dto.toRoomSearchResult
import com.example.home_rent_app.data.model.BookmarkRequest
import com.example.home_rent_app.data.model.RoomSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindRoomRepositoryImpl @Inject constructor(
    private val dataSource: FindRoomDataSource
) : FindRoomRepository {

    override fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly: Boolean,
        sortedBy: String,
        searchAddress: String
    ): Flow<RoomSearchResult> {
        return dataSource.getSearchResult(page, size, availableOnly, sortedBy, searchAddress)
            .map {
                it.toRoomSearchResult()
            }
    }

    override suspend fun addBookmark(
        bookmarkRequest: BookmarkRequest
    ) = dataSource.addBookmark(bookmarkRequest)

    override suspend fun deleteBookmark(
        bookmarkRequest: BookmarkRequest
    ) = dataSource.deleteBookmark(bookmarkRequest)
}

