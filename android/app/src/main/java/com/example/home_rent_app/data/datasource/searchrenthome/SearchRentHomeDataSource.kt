package com.example.home_rent_app.data.datasource.searchrenthome

import com.example.home_rent_app.data.dto.RoomSearchResultDTO
import kotlinx.coroutines.flow.Flow

interface SearchRentHomeDataSource {

    fun getSearchResult(
        page: Int,
        size: Int,
        availableOnly: Boolean,
        sortedBy: String,
        searchAddress: String
    ): Flow<RoomSearchResultDTO>
}
