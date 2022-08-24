package com.example.home_rent_app.data.repository.detail

import com.example.home_rent_app.data.model.DetailHomeData
import kotlinx.coroutines.flow.Flow

interface DetailRepository {

    fun getHomeDetail(id: Int): Flow<DetailHomeData>
}
