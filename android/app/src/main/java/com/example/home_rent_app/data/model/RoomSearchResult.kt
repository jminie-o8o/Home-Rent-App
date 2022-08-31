package com.example.home_rent_app.data.model

data class RoomSearchResult(
    val hasNext: Boolean,
    val rentArticles: List<Article>
)

data class Article(
    val availableFrom: String,
    val bookmarkCount: Int,
    val completed: Boolean,
    val contractExpiresAt: String,
    val contractType: String,
    val createdAt: String,
    val deleted: Boolean,
    val deposit: Int,
    val houseImage: String,
    val id: Int,
    val rentFee: Int,
    val title: String
)
