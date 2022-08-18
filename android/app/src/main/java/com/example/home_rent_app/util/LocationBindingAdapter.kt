package com.example.home_rent_app.util

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.home_rent_app.R
import com.google.android.material.chip.ChipGroup

@BindingAdapter("locationTypeFilter")
fun ChipGroup.bindLocationTypeFilter(location: Location?) =
    location?.let { location ->
        when (location) {
            Location.SEOUL -> check(R.id.chip_seoul)
            Location.INCHEON -> check(R.id.chip_incheon)
            Location.BUSAN -> check(R.id.chip_busan)
            Location.DAEJEON -> check(R.id.chip_daejeon)
            Location.ULSAN -> check(R.id.chip_ulsan)
            Location.GWANGJU -> check(R.id.chip_gwangju)
            Location.DAEGU -> check(R.id.chip_daegu)
            Location.GYEONGGIDO -> check(R.id.chip_gyeonggido)
            Location.GANGWON -> check(R.id.chip_gangwon)
            Location.CHUNBUK -> check(R.id.chip_chungbuk)
            Location.CHUNNAM -> check(R.id.chip_chungnam)
            Location.JUNBUK -> check(R.id.chip_junbuk)
            Location.JUNNAM -> check(R.id.chip_junnam)
            Location.GYUNGBUK -> check(R.id.chip_gyungbuk)
            Location.GYUNGNAM -> check(R.id.chip_gyungnam)
        }
    }

@InverseBindingAdapter(attribute = "locationTypeFilter")
fun ChipGroup.convertToLocationTypeFilter(): Location = when (checkedChipId) {
    R.id.chip_seoul -> Location.SEOUL
    R.id.chip_incheon -> Location.INCHEON
    R.id.chip_busan -> Location.BUSAN
    R.id.chip_daejeon -> Location.DAEJEON
    R.id.chip_ulsan -> Location.ULSAN
    R.id.chip_gwangju -> Location.GWANGJU
    R.id.chip_daegu -> Location.DAEGU
    R.id.chip_gyeonggido -> Location.GYEONGGIDO
    R.id.chip_gangwon -> Location.GANGWON
    R.id.chip_chungbuk -> Location.CHUNBUK
    R.id.chip_chungnam -> Location.CHUNNAM
    R.id.chip_junbuk -> Location.JUNBUK
    R.id.chip_junnam -> Location.JUNNAM
    R.id.chip_gyungbuk -> Location.GYUNGBUK
    else -> Location.GYUNGNAM
}

@BindingAdapter("locationTypeFilterAttrChanged")
fun ChipGroup.setLocationListeners(attrChange: InverseBindingListener?) =
    setOnCheckedStateChangeListener { _, _ -> attrChange?.onChange() }
