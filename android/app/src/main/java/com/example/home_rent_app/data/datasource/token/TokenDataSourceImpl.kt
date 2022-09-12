package com.example.home_rent_app.data.datasource.token

import com.example.home_rent_app.data.datastore.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenDataSourceImpl @Inject constructor(
    private val dataStore: DataStore
) : TokenDataSource {

    override suspend fun saveToken(token: List<String>) {
        if (token.isNotEmpty()) {
            dataStore.saveToken(token)
        }
    }

    override suspend fun getToken(): Flow<List<String>> {
        return dataStore.getToken()
    }

    override fun setAppSession(token: List<String>) {
        dataStore.setAppSession(token)
    }
}
