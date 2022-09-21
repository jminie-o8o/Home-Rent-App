package com.example.home_rent_app.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddWantHomeResponseDTO(
    @field:Json(name = "id")
    val id: Int
)
