package com.example.home_rent_app.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import hirondelle.date4j.DateTime

@BindingAdapter("createDate")
fun setCreateDate(textView: TextView, date: String?) {
    date?.let {
        val dateTime = DateTime(date)
        val createDate = dateTime.format("YY/MM/DD")
        textView.text = textView.context.getString(R.string.create_date, createDate)
    }
}

@BindingAdapter("date")
fun setDate(textView: TextView, date: String?) {
    date?.let {
        val dateTime = DateTime(date)
        val createDate = dateTime.format("YY/MM/DD")
        textView.text = createDate
    }
}