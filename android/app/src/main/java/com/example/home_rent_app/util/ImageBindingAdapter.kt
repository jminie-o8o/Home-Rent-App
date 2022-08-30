package com.example.home_rent_app.util

import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun setImage(view: de.hdodenhof.circleimageview.CircleImageView, imageUrl: String?) {
    view.load(imageUrl)
}
