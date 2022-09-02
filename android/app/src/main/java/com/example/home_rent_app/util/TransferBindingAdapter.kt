package com.example.home_rent_app.util

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.home_rent_app.R
import com.google.android.material.chip.ChipGroup

@BindingAdapter("roomTypeFilter")
fun ChipGroup.bindRoomTypeFilter(roomType: HouseType?) =
    roomType?.let { filter ->
        when (filter) {
            HouseType.ONE_ROOM -> check(R.id.chip_one_room)
            HouseType.TWO_ROOM -> check(R.id.chip_two_room)
            HouseType.EFFICIENCY -> check(R.id.chip_efficiency)
            HouseType.SHARE_HOUSE -> check(R.id.chip_share_room)
            HouseType.THREE_ROOM -> check(R.id.chip_three_room)
        }
    }

@InverseBindingAdapter(attribute = "roomTypeFilter")
fun ChipGroup.convertToRoomTypeFilter(): HouseType = when (checkedChipId) {
    R.id.chip_one_room -> HouseType.ONE_ROOM
    R.id.chip_two_room -> HouseType.TWO_ROOM
    R.id.chip_efficiency -> HouseType.EFFICIENCY
    R.id.chip_share_room -> HouseType.SHARE_HOUSE
    else -> HouseType.THREE_ROOM
}

@BindingAdapter("roomTypeFilterAttrChanged")
fun ChipGroup.setRoomTypeListeners(attrChange: InverseBindingListener?) =
    setOnCheckedStateChangeListener { _, _ -> attrChange?.onChange() }

@BindingAdapter("rentTypeFilter")
fun ChipGroup.bindRentTypeFilter(rentType: RentType?) =
    rentType?.let { filter ->
        when (filter) {
            RentType.MONTHLY -> check(R.id.chip_monthly)
            RentType.JEONSE -> check(R.id.chip_jeonse)
        }
    }

@InverseBindingAdapter(attribute = "rentTypeFilter")
fun ChipGroup.convertToRentTypeFilter(): RentType = when (checkedChipId) {
    R.id.chip_monthly -> RentType.MONTHLY
    else -> RentType.JEONSE
}

@BindingAdapter("rentTypeFilterAttrChanged")
fun ChipGroup.setRentTypeListeners(attrChange: InverseBindingListener?) =
    setOnCheckedStateChangeListener { _, _ -> attrChange?.onChange() }
