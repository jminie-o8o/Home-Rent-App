package com.example.home_rent_app.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiveBookmarkResponseDTO(
    @Json(name = "hasNext")
    val hasNext: Boolean,
    @Json(name = "rentArticles")
    val rentArticles: List<RentArticleBookmark>
)

@JsonClass(generateAdapter = true)
data class RentArticleBookmark(
    @Json(name = "availableFrom")
    val availableFrom: String,
    @Json(name = "bookmarkCount")
    val bookmarkCount: Int,
    @Json(name = "bookmarked")
    val bookmarked: Boolean,
    @Json(name = "completed")
    val completed: Boolean,
    @Json(name = "contractExpiresAt")
    val contractExpiresAt: String,
    @Json(name = "contractType")
    val contractType: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "deleted")
    val deleted: Boolean,
    @Json(name = "deposit")
    val deposit: Int,
    @Json(name = "houseImage")
    val houseImage: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "rentFee")
    val rentFee: Int,
    @Json(name = "title")
    val title: String
)
