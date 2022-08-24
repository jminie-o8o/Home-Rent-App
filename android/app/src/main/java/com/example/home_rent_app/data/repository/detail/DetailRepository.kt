package com.example.home_rent_app.data.repository.detail

import com.example.home_rent_app.data.dto.DetailHomeDTO
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.JWT
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DetailRepository {

    suspend fun getHomeDetail(id: Int): DetailHomeData

    suspend fun refreshToken(): JWT
}
