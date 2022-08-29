package com.example.home_rent_app.data.datasource.map

import com.example.home_rent_app.data.dto.MapResponseDTO

interface MapDataSource {

    suspend fun getAddress(name: String): MapResponseDTO
}