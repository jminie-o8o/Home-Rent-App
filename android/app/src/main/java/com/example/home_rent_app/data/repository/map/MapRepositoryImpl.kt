package com.example.home_rent_app.data.repository.map

import com.example.home_rent_app.data.datasource.map.MapDataSource
import com.example.home_rent_app.data.dto.toMapResponse
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val dataSource: MapDataSource
) : MapRepository {

    override suspend fun getAddress(name: String) = dataSource.getAddress(name).toMapResponse()
}
