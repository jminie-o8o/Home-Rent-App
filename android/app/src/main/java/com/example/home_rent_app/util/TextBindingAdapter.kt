package com.example.home_rent_app.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@BindingAdapter("formattingTime")
fun timeFormat(view: TextView, stringTime: String) {
    val format = SimpleDateFormat(view.context.getString(R.string.time_format))
    val time = format.parse(stringTime)
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
