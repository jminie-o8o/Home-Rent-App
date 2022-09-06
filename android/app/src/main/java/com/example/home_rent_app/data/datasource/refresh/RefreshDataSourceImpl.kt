package com.example.home_rent_app.data.datasource.refresh

import com.example.home_rent_app.data.api.TokenRefreshApi
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.util.logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshDataSourceImpl @Inject constructor(
    private val api: TokenRefreshApi
) : RefreshDataSource {

    override suspend fun refreshAuthToken(): OAuthTokenResponse {
        logger("dataSource : ${api.getAuthToken()}")
        return  api.getAuthToken()
    }

}
