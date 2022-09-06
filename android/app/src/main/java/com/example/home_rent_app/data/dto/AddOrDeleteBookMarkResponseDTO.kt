package com.example.home_rent_app.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddOrDeleteBookMarkResponseDTO(
    @field:Json(name = "code")
    val code: Int,
    @field:Json(name = "message")
    val message: String
)
