package com.example.home_rent_app.data.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeleteHomeResponseDTO(
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "statusCode")
    val statusCode: Int
)
