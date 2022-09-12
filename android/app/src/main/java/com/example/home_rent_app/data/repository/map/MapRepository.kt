package com.example.home_rent_app.data.repository.map

import com.example.home_rent_app.data.model.MapResponse

interface MapRepository {

    suspend fun getAddress(name: String): MapResponse
}
