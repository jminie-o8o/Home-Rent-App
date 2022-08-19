package com.example.home_rent_app.data.repository.findroom

import com.example.home_rent_app.data.model.RoomSearchResult
import kotlinx.coroutines.flow.Flow

interface FindRoomRepository {

    fun getSearchResult(
        page: Int = 0,
        size: Int = 100,
        isCompleted : Boolean = false,
        sortedBy: String = "",
        searchAddress: String
    ): Flow<RoomSearchResult>

}