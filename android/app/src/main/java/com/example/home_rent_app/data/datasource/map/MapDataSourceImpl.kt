package com.example.home_rent_app.data.datasource.map

import com.example.home_rent_app.data.api.MapApi
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(
    private val api: MapApi
) : MapDataSource {

    override suspend fun getAddress(name: String) = api.getAddress(name = name)
}
