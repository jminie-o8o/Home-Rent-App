package com.example.home_rent_app.data.repository.token

import com.example.home_rent_app.data.datasource.token.TokenDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
