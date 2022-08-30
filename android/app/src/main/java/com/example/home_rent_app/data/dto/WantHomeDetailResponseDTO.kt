package com.example.home_rent_app.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WantHomeDetailResponseDTO(
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "bookmarkCount")
    val bookmarkCount: Int,
    @field:Json(name = "content")
    val content: String,
    @field:Json(name = "createdAt")
    val createdAt: String,
    @field:Json(name = "depositBudget")
    val depositBudget: Int,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "modifiedAt")
    val modifiedAt: String,
    @field:Json(name = "moveInDate")
    val moveInDate: String,
    @field:Json(name = "moveOutDate")
    val moveOutDate: String,
    @field:Json(name = "rentBudget")
    val rentBudget: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "viewCount")
    val viewCount: Int
)


@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "displayName")
    val displayName: String,
    @field:Json(name = "gender")
    val gender: String,
    @field:Json(name = "profileImageUrl")
    val profileImageUrl: String,
    @field:Json(name = "userId")
    val userId: Int
)
