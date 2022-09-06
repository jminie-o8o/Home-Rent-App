package com.example.home_rent_app.data.dto

import com.example.home_rent_app.data.model.Article
import com.example.home_rent_app.data.model.RoomSearchResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomSearchResultDTO(
    @field:Json(name = "hasNext")
    val hasNext: Boolean?,
    @field:Json(name = "rentArticles")
    val rentArticles: List<RentArticle?> = emptyList()
)

@JsonClass(generateAdapter = true)
data class RentArticle(
    @field:Json(name = "availableFrom")
    val availableFrom: String?,
    @field:Json(name = "bookmarkCount")
    val bookmarkCount: Int = 0,
    @field:Json(name = "completed")
    val completed: Boolean?,
    @field:Json(name = "contractExpiresAt")
    val contractExpiresAt: String?,
    @field:Json(name = "contractType")
    val contractType: String?,
    @field:Json(name = "createdAt")
    val createdAt: String?,
    @field:Json(name = "deleted")
    val deleted: Boolean?,
    @field:Json(name = "deposit")
    val deposit: Int?,
    @field:Json(name = "houseImages")
    val houseImages: List<String?> = emptyList(),
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "rentFee")
    val rentFee: Int?,
    @field:Json(name = "title")
    val title: String = "",
    @field:Json(name = "address")
    val address: String = "",
    @field:Json(name = "bookmarked")
    val bookmarked: Boolean? = false
)

fun RoomSearchResultDTO.toRoomSearchResult(): RoomSearchResult {
    val hasNext = requireNotNull(hasNext)
    return RoomSearchResult(
        hasNext,
        rentArticles.map { requireNotNull(it).toArticle() }
    )
}

fun RentArticle.toArticle(): Article {
    return Article(
        availableFrom = requireNotNull(availableFrom),
        bookmarkCount = bookmarkCount,
        completed = requireNotNull(completed),
        contractExpiresAt = requireNotNull(contractExpiresAt),
        contractType = requireNotNull(contractType),
        createdAt = requireNotNull(createdAt),
        deleted = requireNotNull(deleted),
        deposit = requireNotNull(deposit),
        houseImages = houseImages.filterNotNull(),
        id = requireNotNull(id),
        rentFee = requireNotNull(rentFee),
        title = title,
        bookmarked = requireNotNull(bookmarked)
    )
}
