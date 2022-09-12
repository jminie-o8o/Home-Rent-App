package com.example.home_rent_app.data.repository.searchrenthome

import com.example.home_rent_app.data.model.RoomSearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRentHomeRepository {

    fun getSearchResult(
        page: Int = 0,
        size: Int = 100,
        availableOnly: Boolean = false,
        sortedBy: String = "rentFee",
        searchAddress: String
    ): Flow<RoomSearchResult>
}
