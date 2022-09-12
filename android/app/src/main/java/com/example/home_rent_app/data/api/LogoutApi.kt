package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.LogoutResponseDTO
import retrofit2.http.POST

interface LogoutApi {

    @POST("logout")
    suspend fun logout(): LogoutResponseDTO
}
