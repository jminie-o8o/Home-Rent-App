package com.example.home_rent_app.data.model

import com.example.home_rent_app.data.dto.WantedArticleDTO

data class WantHomeResult(
    val hasNext: Boolean,
    val wantedArticles: List<WantedArticleDTO>
)

data class WantedArticle(
    val address: String,
    val content: String,
    val createdAt: String,
    val depositBudget: Int,
    val id: Int,
    val moveInDate: String,
    val moveOutDate: String,
    val rentBudget: Int,
    val title: String
)
