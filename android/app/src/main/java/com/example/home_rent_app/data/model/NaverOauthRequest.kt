package com.example.home_rent_app.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NaverOauthRequest(
    val authCode: String,
    val oauthClientName: String = "NAVER"
)
