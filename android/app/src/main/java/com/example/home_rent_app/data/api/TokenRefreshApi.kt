package com.example.home_rent_app.data.api

import com.example.home_rent_app.data.dto.OAuthTokenResponse
import retrofit2.http.POST

interface TokenRefreshApi {

    @POST("login/refresh")
    suspend fun getAuthToken(): OAuthTokenResponse

}