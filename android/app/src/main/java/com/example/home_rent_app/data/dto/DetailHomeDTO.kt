package com.example.home_rent_app.data.dto


import com.example.home_rent_app.data.model.DetailHomeData
import com.example.home_rent_app.data.model.UserData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailHomeDTO(
    @Json(name = "address")
    val address: String?,
    @Json(name = "addressDescription")
    val addressDescription: String = "",
    @Json(name = "addressDetail")
    val addressDetail: String = "",
    @Json(name = "availableFrom")
    val availableFrom: String?,
    @Json(name = "completed")
    val completed: Boolean?,
    @Json(name = "content")
    val content: String = "",
    @Json(name = "contractExpiresAt")
    val contractExpiresAt: String?,
    @Json(name = "contractType")
    val contractType: String?,
    @Json(name = "createdAt")
    val createdAt: String?,
    @Json(name = "deposit")
    val deposit: Int?,
    @Json(name = "facilities")
    val facilities: List<String?> = emptyList(),
    @Json(name = "hasBalcony")
    val hasBalcony: Boolean = false,
    @Json(name = "hasElevator")
    val hasElevator: Boolean = false,
    @Json(name = "hasParkingLot")
    val hasParkingLot: Boolean = false,
    @Json(name = "houseImages")
    val houseImages: List<String?>?,
    @Json(name = "houseType")
    val houseType: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "latitude")
    val latitude: Double = 0.0,
    @Json(name = "longitude")
    val longitude: Double = 0.0,
    @Json(name = "maintenanceFee")
    val maintenanceFee: Int?,
    @Json(name = "maintenanceFeeDescription")
    val maintenanceFeeDescription: String = "",
    @Json(name = "maxFloor")
    val maxFloor: Int?,
    @Json(name = "modifiedAt")
    val modifiedAt: String = "",
    @Json(name = "rentFee")
    val rentFee: Int?,
    @Json(name = "securityFacilities")
    val securityFacilities: List<String?> = emptyList(),
    @Json(name = "thisFloor")
    val thisFloor: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "user")
    val user: User?
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "userId")
    val userId: Int? = 0,
    @Json(name = "displayName")
    val displayName: String?,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String?
)

fun DetailHomeDTO.toDetailHomeData(): DetailHomeData {
    return DetailHomeData(
        address = requireNotNull(address),
        addressDescription = addressDescription,
        addressDetail = addressDetail,
        availableFrom = requireNotNull(availableFrom),
        completed = requireNotNull(completed),
        content = content,
        contractExpiresAt = requireNotNull(contractExpiresAt),
        contractType = requireNotNull(contractType),
        createdAt = requireNotNull(createdAt),
        deposit = requireNotNull(deposit),
        facilities = facilities.map { it.orEmpty() },
        hasBalcony = hasBalcony,
        hasElevator = hasElevator,
        hasParkingLot = hasParkingLot,
        houseImages = requireNotNull(houseImages).map { it.orEmpty() },
        houseType = requireNotNull(houseType),
        id = requireNotNull(id),
        latitude = latitude,
        longitude = longitude,
        maintenanceFee = requireNotNull(maintenanceFee),
        maintenanceFeeDescription = maintenanceFeeDescription,
        maxFloor = requireNotNull(maxFloor),
        modifiedAt = modifiedAt,
        rentFee = requireNotNull(rentFee),
        securityFacilities = securityFacilities.map { it.orEmpty() },
        thisFloor = requireNotNull(thisFloor),
        title = requireNotNull(title),
        user = requireNotNull(user).toUserData()
    )
}

fun User.toUserData() =
    UserData(
//        requireNotNull(userId),
        displayName = requireNotNull(displayName),
        gender = requireNotNull(gender),
        profileImageUrl = requireNotNull(profileImageUrl)
    )