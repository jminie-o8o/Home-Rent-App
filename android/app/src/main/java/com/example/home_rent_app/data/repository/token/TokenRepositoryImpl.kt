package com.example.home_rent_app.data.repository.token

import androidx.datastore.dataStore
import androidx.datastore.preferences.core.emptyPreferences
import com.example.home_rent_app.data.datasource.token.TokenDataSource
import com.example.home_rent_app.data.model.AccessToken
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.RefreshToken
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataSource: TokenDataSource
) : TokenRepository {

    override suspend fun saveToken(token: List<String>) {
        dataSource.saveToken(token)
    }

    override suspend fun getToken() = dataSource.getToken()

    override fun setAppSession(token: List<String>) {
        dataSource.setAppSession(token)
    }
}