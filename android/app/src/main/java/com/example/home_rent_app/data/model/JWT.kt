package com.example.home_rent_app.data.model

data class JWT(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken
)

data class AccessToken(
    val tokenCode: String
)

data class RefreshToken(
    val tokenCode: String
)
