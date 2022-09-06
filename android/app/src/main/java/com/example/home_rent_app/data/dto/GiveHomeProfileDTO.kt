package com.example.home_rent_app.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiveHomeProfileDTO(
    @field:Json(name = "hasNext")
    val hasNext: Boolean,
    @field:Json(name = "rentArticles")
    val rentArticles: List<RentArticleProfile>
)

@JsonClass(generateAdapter = true)
data class RentArticleProfile(
    @field:Json(name = "availableFrom")
    val availableFrom: String,
    @field:Json(name = "bookmarkCount")
    val bookmarkCount: Int,
    @field:Json(name = "completed")
    val completed: Boolean,
    @field:Json(name = "contractExpiresAt")
    val contractExpiresAt: String,
    @field:Json(name = "contractType")
    val contractType: String,
    @field:Json(name = "createdAt")
    val createdAt: String,
    @field:Json(name = "deleted")
    val deleted: Boolean,
    @field:Json(name = "deposit")
    val deposit: Int,
    @field:Json(name = "houseImage")
    val houseImage: List<String>,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "rentFee")
    val rentFee: Int,
    @field:Json(name = "title")
    val title: String
)
