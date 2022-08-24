package com.example.home_rent_app.data.datasource.detailHome

import com.example.home_rent_app.data.api.DetailHomeApi
import com.example.home_rent_app.data.api.TokenRefreshApi
import com.example.home_rent_app.data.dto.DetailHomeDTO
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailHomeDataSourceImpl @Inject constructor(
    private val  api: DetailHomeApi,
    private val refreshApi: TokenRefreshApi
): DetailHomeDataSource {

    override suspend fun getDetailHome(id: Int) = api.getDetailHomeDTO(id)

    override suspend fun refreshToken() = refreshApi.getAuthToken()

}