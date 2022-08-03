package com.example.home_rent_app.data.model

data class KakaoOauthRequest(
    val authCode: String,
    val oauthClientName: String = "kakao"
)
