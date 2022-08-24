package com.example.home_rent_app.data.datasource.detailHome

import com.example.home_rent_app.data.dto.DetailHomeDTO
import kotlinx.coroutines.flow.Flow

interface DetailHomeDataSource {

    fun getDetailHome(id: Int): Flow<DetailHomeDTO>
}