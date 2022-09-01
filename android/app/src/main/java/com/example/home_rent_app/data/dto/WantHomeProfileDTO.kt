package com.example.home_rent_app.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WantHomeProfileDTO(
    @field:Json(name = "hasNext")
    val hasNext: Boolean,
    @field:Json(name = "wantedArticles")
    val wantedArticles: List<WantArticleProfile>
)

@JsonClass(generateAdapter = true)
data class WantArticleProfile(
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "content")
    val content: String,
    @field:Json(name = "createdAt")
    val createdAt: String,
    @field:Json(name = "depositBudget")
    val depositBudget: Int,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "moveInDate")
    val moveInDate: String,
    @field:Json(name = "moveOutDate")
    val moveOutDate: String,
    @field:Json(name = "rentBudget")
    val rentBudget: Int,
    @field:Json(name = "title")
    val title: String
)
