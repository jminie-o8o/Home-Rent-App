package com.example.home_rent_app.data.dto

import com.example.home_rent_app.data.model.User
import com.example.home_rent_app.data.model.UserData
import com.example.home_rent_app.util.Constants
import com.example.home_rent_app.util.logger
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @field:Json(name = "userId")
    val userId: Int?,
    @field:Json(name = "displayName")
    val displayName: String?,
    @field:Json(name = "profileImageUrl")
    val profileImageUrl: String?,
    @field:Json(name = "gender")
    val gender: String?
)

fun UserDTO.toUserData() =
    UserData(
        requireNotNull(userId),
        displayName = requireNotNull(displayName),
        gender = requireNotNull(gender),
        profileImageUrl = requireNotNull(profileImageUrl)
    )

fun OAuthTokenResponse.toUser(): User {
    logger("gender at toUser : ${user.gender}")
    return User(
        user.userId ?: -1,
        user.displayName.orEmpty(),
        user.profileImageUrl.orEmpty(),
        user.gender ?: Constants.GENDER_NEW
    )
}