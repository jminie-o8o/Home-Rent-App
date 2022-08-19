package com.example.home_rent_app.data.datasource.findroom

import com.example.home_rent_app.data.api.FindRoomApi
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindRoomDataSourceImpl @Inject constructor(
    private val api: FindRoomApi
) : FindRoomDataSource {

    override fun getSearchResult(
        page: Int,
        size: Int,
        isCompleted : Boolean,
        sortedBy: String,
        searchAddress: String
    ) = flow {
        emit(api.getSearchResult(page, size, isCompleted, sortedBy, searchAddress))
    }

}