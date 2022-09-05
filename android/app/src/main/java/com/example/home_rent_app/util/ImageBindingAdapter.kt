package com.example.home_rent_app.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.home_rent_app.R

@BindingAdapter("imageUrl")
fun setImage(view: de.hdodenhof.circleimageview.CircleImageView, imageUrl: String) {
    view.load(imageUrl)
}

@BindingAdapter("imageSetting")
fun setNormalImage(imageView: ImageView, imageUrl: String?) {
    if(imageUrl == null) {
        imageView.setImageResource(R.drawable.default_profile_image)
        return
    }
    imageView.load(imageUrl) {
        error(R.drawable.default_profile_image)
    }
}