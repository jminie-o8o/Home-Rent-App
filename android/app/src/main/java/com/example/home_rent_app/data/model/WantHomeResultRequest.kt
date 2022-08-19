package com.example.home_rent_app.data.model

data class WantHomeResultRequest(
    val page: Int,
    val size: Int,
    val keyword: String,
    val availableOnly: Boolean
)
