package com.example.home_rent_app.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.home_rent_app.R

@BindingAdapter("imageUrl")
fun setImage(view: de.hdodenhof.circleimageview.CircleImageView, imageUrl: String?) {
    view.load(imageUrl) {
        error(R.drawable.ic_baseline_account_circle_24)
    }
}

@BindingAdapter("imageSetting")
fun setNormalImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl == null) {
        imageView.load(R.drawable.ic_baseline_account_circle_24) {
            transformations(CircleCropTransformation())
        }
        return
    }
    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    imageView.load(imageUrl) {
        placeholder(circularProgressDrawable)
        error(R.drawable.ic_baseline_account_circle_24)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("roundImage")
fun setRoundImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl == null) {
        return
    }
    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    imageView.load(imageUrl) {
        placeholder(circularProgressDrawable)
        transformations(RoundedCornersTransformation(30f, 30f, 30f, 30f))
        error(R.drawable.ic_image_not)
    }
}

@BindingAdapter("deleteCheck")
fun setDeleteView(textView: TextView, delete: Boolean) {
    if(delete) {
        textView.apply {
            visibility = View.VISIBLE
            text = "✓삭제됨"
        }
    } else {
        textView.apply {
            visibility = View.GONE
        }
    }
}
