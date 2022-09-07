package com.example.home_rent_app.data.datasource.refresh

import android.content.Context
import com.example.home_rent_app.data.api.TokenRefreshApi
import com.example.home_rent_app.data.dto.OAuthTokenResponse
import com.example.home_rent_app.data.datastore.DataStore
import com.example.home_rent_app.ui.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshDataSourceImpl @Inject constructor(
    private val api: TokenRefreshApi,
    private val dataStore: DataStore,
    @ApplicationContext private val context: Context
) : RefreshDataSource {

    override suspend fun refreshAuthToken(): OAuthTokenResponse? {
        val response = kotlin.runCatching {
            api.getAuthToken()
        }.getOrNull()
        if (response == null) {
            context.startActivity(LoginActivity.newInstance(context))
            clearDataStore()
            return null
        }
        return response
    }

    override suspend fun clearDataStore() {
        dataStore.clearDataStore()
    }
}
