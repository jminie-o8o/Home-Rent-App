package com.example.home_rent_app.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@BindingAdapter("formattingTime")
fun timeFormat(view: TextView, stringTime: String?) {
    val format = SimpleDateFormat(view.context.getString(R.string.time_format))
    val time = stringTime?.let { format.parse(it) }
    val result = time?.let { calculateTime(time) } ?: ""
    view.text = result
}

@BindingAdapter("formattingStartDate")
fun startDateFormat(view: TextView, stringDate: String) {
    val month: String = if (stringDate.slice(5..6).startsWith("0")) stringDate[6].toString()
    else stringDate.slice(5..6)

    val day: String = if (stringDate.slice(8..9).startsWith("0")) stringDate[9].toString()
    else stringDate.slice(8..9)

    view.text = view.context.getString(R.string.start_date_format, month, day)
}

@BindingAdapter("formattingEndDate")
fun endDateFormat(view: TextView, stringDate: String) {
    val month: String = if (stringDate.slice(5..6).startsWith("0")) stringDate[6].toString()
    else stringDate.slice(5..6)

    val day: String = if (stringDate.slice(8..9).startsWith("0")) stringDate[9].toString()
    else stringDate.slice(8..9)

    view.text = view.context.getString(R.string.end_date_format, month, day)
}

@BindingAdapter("deposit", "monthlyPay")
fun depositFormat(view: TextView, deposit: String, monthlyPay: String) {
    view.text = view.context.getString(R.string.deposit_format, deposit, monthlyPay)
}

@BindingAdapter("gender")
fun genderFormat(view: TextView, gender: String?) {
    if (gender == "MALE") view.text = "남"
    else view.text = "여"
}

@BindingAdapter("depositSimple")
fun depositSimpleFormat(view: TextView, deposit: String?) {
    view.text = view.context.getString(R.string.deposit_simple, deposit)
}

@BindingAdapter("monthlyPaySimple")
fun monthlySimpleFormat(view: TextView, monthlyPay: String?) {
    view.text = view.context.getString(R.string.monthly_simple, monthlyPay)
}

@BindingAdapter("hasOrNo")
fun setHas(textView: TextView, has: Boolean?) {
    has?.let {
        if (has) {
            textView.text = "있음"
        } else {
            textView.text = "없음"
        }
    }
}

@BindingAdapter("possibleOrImpossible")
fun setHasParking(textView: TextView, has: Boolean?) {
    has?.let {
        if (has) {
            textView.text = "가능"
        } else {
            textView.text = "불가"
        }
    }
}

@BindingAdapter("homeType")
fun setHomeType(textView: TextView, type: String?) {
    when(type) {
        RentType.MONTHLY.value -> {
            textView.text = "월세"
        }
        RentType.JEONSE.value -> {
            textView.text = "전세"
        }
        HouseType.ONE_ROOM.value -> {
            textView.text = "원룸"
        }
        HouseType.TWO_ROOM.value -> {
            textView.text = "투룸"
        }
        HouseType.THREE_ROOM.value -> {
            textView.text = "쓰리룸"
        }
        HouseType.SHARE_HOUSE.value -> {
            textView.text = "쉐어하우스"
        }
        HouseType.EFFICIENCY.value -> {
            textView.text = "오피스텔"
        }
        else -> textView.text = ""
    }
}