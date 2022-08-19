package com.example.home_rent_app.data.repository.findroom

import com.example.home_rent_app.data.datasource.findroom.FindRoomDataSource
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.dto.toRoomSearchResult
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.RoomSearchResult
import com.example.home_rent_app.util.logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindRoomRepositoryImpl @Inject constructor(
    private val dataSource: FindRoomDataSource
): FindRoomRepository {

    override fun getSearchResult(
        page: Int,
        size: Int,
        isCompleted : Boolean,
        sortedBy: String,
        searchAddress: String
    ): Flow<RoomSearchResult> {
        return dataSource.getSearchResult(page, size, isCompleted, sortedBy, searchAddress)
        .map {
            it.toRoomSearchResult()
        }
    }

    override fun refreshAuthToken(): Flow<JWT> {
        return dataSource.refreshAuthToken().map { it.toJWT() }
    }

}