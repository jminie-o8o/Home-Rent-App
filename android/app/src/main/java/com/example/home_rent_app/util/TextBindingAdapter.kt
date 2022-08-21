package com.example.home_rent_app.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@BindingAdapter("formattingDate")
fun dateFormat(view: TextView, stringDate: String) {
    val format = SimpleDateFormat(view.context.getString(R.string.date_format))
    val date = format.parse(stringDate)
    val result = date?.let { calculateTime(date) } ?: ""
    view.text = result
}
