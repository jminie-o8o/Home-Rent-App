package com.example.home_rent_app.data.repository.searchrenthome

import com.example.home_rent_app.data.datasource.searchrenthome.SearchRentHomeDataSource
import com.example.home_rent_app.data.dto.toRoomSearchResult
import com.example.home_rent_app.data.model.RoomSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRentHomeRepositoryImpl @Inject constructor(
    private val dataSource: SearchRentHomeDataSource
) : SearchRentHomeRepository {

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
}

