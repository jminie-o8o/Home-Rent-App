package com.example.home_rent_app.data.repository.detail

import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.JWT

interface DetailRepository {

    suspend fun getHomeDetail(id: Int): DetailHomeData

    suspend fun refreshToken(): JWT
}
