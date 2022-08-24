package com.example.home_rent_app.data.dto

import com.example.home_rent_app.data.model.UserData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "userId")
    val userId: Int? = 0,
    @Json(name = "displayName")
    val displayName: String?,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String?
)

fun UserDTO.toUserData() =
    UserData(
//        requireNotNull(userId),
        displayName = requireNotNull(displayName),
        gender = requireNotNull(gender),
        profileImageUrl = requireNotNull(profileImageUrl)
    )
