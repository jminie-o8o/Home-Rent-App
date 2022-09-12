package com.example.home_rent_app.data.repository.token

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    suspend fun saveToken(token: List<String>)

    suspend fun getToken(): Flow<List<String>>

    fun setAppSession(token: List<String>)
}
