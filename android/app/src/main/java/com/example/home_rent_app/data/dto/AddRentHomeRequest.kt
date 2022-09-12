package com.example.home_rent_app.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddRentHomeRequest(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "address")
    val address: String,
    @Json(name = "addressDescription")
    val addressDescription: String,
    @Json(name = "addressDetail")
    val addressDetail: String,
    @Json(name = "availableFrom")
    val availableFrom: String,
    @Json(name = "content")
    val content: String,
    @Json(name = "contractType")
    val contractType: String,
    @Json(name = "deposit")
    val deposit: Int,
    @Json(name = "contractExpiresAt")
    val contractExpiresAt: String,
    @Json(name = "facilities")
    val facilities: List<String>,
    @Json(name = "hasBalcony")
    val hasBalcony: Boolean,
    @Json(name = "hasElevator")
    val hasElevator: Boolean,
    @Json(name = "hasParkingLot")
    val hasParkingLot: Boolean,
    @Json(name = "houseImages")
    val houseImages: List<String>,
    @Json(name = "houseType")
    val houseType: String,
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "maintenanceFee")
    val maintenanceFee: Int,
    @Json(name = "maintenanceFeeDescription")
    val maintenanceFeeDescription: String,
    @Json(name = "maxFloor")
    val maxFloor: Int,
    @Json(name = "rentFee")
    val rentFee: Int,
    @Json(name = "securityFacilities")
    val securityFacilities: List<String>,
    @Json(name = "thisFloor")
    val thisFloor: Int,
    @Json(name = "title")
    val title: String
)
