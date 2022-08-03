package com.example.home_rent_app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OAuthTokenResponse(
    @field:Json(name = "accessToken")
    val accessToken: AccessToken,
    @field:Json(name = "refreshToken")
    val refreshToken: RefreshToken
)

@JsonClass(generateAdapter = true)
data class AccessToken(
    @field:Json(name = "expiresAt")
    val expiresAt: String,
    @field:Json(name = "tokenCode")
    val tokenCode: String
)

@JsonClass(generateAdapter = true)
data class RefreshToken(
    @field:Json(name = "expiresAt")
    val expiresAt: String,
    @field:Json(name = "tokenCode")
    val tokenCode: String
)
