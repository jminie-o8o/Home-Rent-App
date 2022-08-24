package com.example.home_rent_app.data.datasource.refresh

import com.example.home_rent_app.data.api.TokenRefreshApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshDataSourceImpl @Inject constructor(
    private val api: TokenRefreshApi
) : RefreshDataSource {

    override suspend fun refreshAuthToken() = api.getAuthToken()

}