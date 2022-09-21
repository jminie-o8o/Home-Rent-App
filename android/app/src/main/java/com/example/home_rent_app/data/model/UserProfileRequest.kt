package com.example.home_rent_app.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileRequest(
    val displayName: String?,
    val profileImageUrl: String,
    val gender: String
)
