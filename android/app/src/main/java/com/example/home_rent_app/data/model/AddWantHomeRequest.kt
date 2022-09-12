package com.example.home_rent_app.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddWantHomeRequest(
    val userId: Int,
    val address: String,
    val title: String,
    val content: String,
    val moveInDate: String,
    val moveOutDate: String,
    val depositBudget: Int,
    val rentBudget: Int
)
