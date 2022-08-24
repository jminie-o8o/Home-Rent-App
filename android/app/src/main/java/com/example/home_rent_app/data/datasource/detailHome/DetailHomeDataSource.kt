package com.example.home_rent_app.data.datasource.detailHome

import com.example.home_rent_app.data.dto.DetailHomeDTO
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DetailHomeDataSource {

    suspend fun getDetailHome(id: Int): DetailHomeDTO

    suspend fun refreshToken(): OAuthTokenResponse

}