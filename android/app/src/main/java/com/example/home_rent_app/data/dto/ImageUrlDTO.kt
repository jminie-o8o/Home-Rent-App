package com.example.home_rent_app.data.dto

import com.example.home_rent_app.data.model.ImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUrlDTO(
    @field:Json(name = "images")
    val images: List<String?> = emptyList()
)

fun ImageUrlDTO.toImageUrl(): ImageUrl {
    return ImageUrl(
        this
            .images
            .map {
                it.orEmpty()
            }
    )
}
