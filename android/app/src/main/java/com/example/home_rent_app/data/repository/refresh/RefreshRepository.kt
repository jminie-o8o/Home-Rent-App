package com.example.home_rent_app.data.repository.refresh

import com.example.home_rent_app.data.model.JWT

interface RefreshRepository {

    suspend fun refreshToken() : JWT

}