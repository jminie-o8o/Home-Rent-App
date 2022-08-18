package com.example.home_rent_app.data.dto


import com.example.home_rent_app.data.model.NickNameCheck
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NickNameCheckDTO(
    @field:Json(name = "isDuplicated")
    val isDuplicated: Boolean
)

fun NickNameCheckDTO.toNickNameCheck(): NickNameCheck {
    return NickNameCheck(
        this.isDuplicated
    )
}
