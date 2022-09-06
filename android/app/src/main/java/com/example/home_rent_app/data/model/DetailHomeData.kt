package com.example.home_rent_app.data.model

data class DetailHomeData(
    val address: String = "",
    val addressDescription: String = "",
    val addressDetail: String = "",
    val availableFrom: String = "",
    val bookmarkCount: Int = 0,
    val completed: Boolean,
    val content: String,
    val contractExpiresAt: String,
    val contractType: String,
    val createdAt: String,
    val deposit: Int,
    val facilities: List<String>,
    val hasBalcony: Boolean,
    val hasElevator: Boolean,
    val hasParkingLot: Boolean,
    val houseImages: List<String>,
    val houseType: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val maintenanceFee: Int,
    val maintenanceFeeDescription: String,
    val maxFloor: Int,
    val modifiedAt: String,
    val rentFee: Int,
    val securityFacilities: List<String>,
    val thisFloor: Int,
    val title: String,
    val user: UserData
)

data class UserData(
    val userId: Int = 0,
    val displayName: String,
    val gender: String,
    val profileImageUrl: String
)
