package com.example.home_rent_app.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetUserInfoDTO(
    @field:Json(name = "accountId")
    val accountId: String,
    @field:Json(name = "displayName")
    val displayName: String,
    @field:Json(name = "gender")
    val gender: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "profileImageUrl")
    val profileImageUrl: String
)
