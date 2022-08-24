package com.example.home_rent_app.data.dto

import com.example.home_rent_app.data.model.AccessToken
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.RefreshToken
import com.example.home_rent_app.data.model.User
import com.example.home_rent_app.util.Constants.GENDER_NEW
import com.example.home_rent_app.util.logger
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OAuthTokenResponse(
    @field:Json(name = "user")
    val user: UserDTO,
    @field:Json(name = "accessToken")
    val accessToken: AccessTokenDTO?,
    @field:Json(name = "refreshToken")
    val refreshToken: RefreshTokenDTO?
)

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

@JsonClass(generateAdapter = true)
data class AccessTokenDTO(
    @field:Json(name = "expiresAt")
    val expiresAt: String?,
    @field:Json(name = "tokenCode")
    val tokenCode: String?
)

@JsonClass(generateAdapter = true)
data class RefreshTokenDTO(
    @field:Json(name = "expiresAt")
    val expiresAt: String?,
    @field:Json(name = "tokenCode")
    val tokenCode: String?
)

fun RefreshTokenDTO.toRefreshToken(): RefreshToken {
    return RefreshToken(tokenCode.orEmpty())
}

fun AccessTokenDTO.toAccessToken(): AccessToken {
    return AccessToken(tokenCode.orEmpty())
}

fun OAuthTokenResponse.toJWT(): JWT {
    val access = requireNotNull(accessToken)
    val refresh = requireNotNull(refreshToken)
    return JWT(access.toAccessToken(), refresh.toRefreshToken())
}

fun OAuthTokenResponse.toUser(): User {
    logger("gender at toUser : ${user.gender}")
    return User(
        user.userId ?: -1,
        user.displayName.orEmpty(),
        user.profileImageUrl.orEmpty(),
        user.gender ?: GENDER_NEW
    )
}
