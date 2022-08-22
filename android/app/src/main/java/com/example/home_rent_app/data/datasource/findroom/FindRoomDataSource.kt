package com.example.home_rent_app.data.datasource.findroom

import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import kotlinx.coroutines.flow.Flow

interface FindRoomDataSource {

    fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly : Boolean,
        sortedBy: String,
        searchAddress: String
    ): Flow<RoomSearchResultDTO>

    fun refreshAuthToken(): Flow<OAuthTokenResponse>
}