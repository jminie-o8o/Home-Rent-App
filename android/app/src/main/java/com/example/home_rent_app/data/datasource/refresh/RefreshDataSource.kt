package com.example.home_rent_app.data.datasource.refresh

import com.example.home_rent_app.data.dto.OAuthTokenResponse

interface RefreshDataSource {

    suspend fun refreshAuthToken(): OAuthTokenResponse?

    suspend fun clearDataStore()
}
