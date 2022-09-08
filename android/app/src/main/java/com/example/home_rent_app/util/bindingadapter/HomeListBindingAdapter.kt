package com.example.home_rent_app.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import com.example.home_rent_app.data.dto.RentArticleBookmark
import com.example.home_rent_app.data.dto.RentArticleProfile
import com.example.home_rent_app.data.model.Article

@BindingAdapter("priceLabel")
fun setPriceLabel(textView: TextView, article: Article) {
    textView.text = if (article.rentFee == 0) {
        textView.context.getString(R.string.deposit, article.deposit)
    } else {
        textView.context.getString(R.string.deposit_and_monthly, article.deposit, article.rentFee)
    }
}

@BindingAdapter("priceLabelAtGiveHome")
fun setPriceLabelAtGiveHome(textView: TextView, article: RentArticleBookmark) {
    textView.text = if (article.rentFee == 0) {
        textView.context.getString(R.string.deposit, article.deposit)
    } else {
        textView.context.getString(R.string.deposit_and_monthly, article.deposit, article.rentFee)
    }
}

@BindingAdapter("priceLabelAtGiveHomeProfile")
fun setPriceLabelAtGiveHomeProfile(textView: TextView, article: RentArticleProfile) {
    textView.text = if (article.rentFee == 0) {
        textView.context.getString(R.string.deposit, article.deposit)
    } else {
        textView.context.getString(R.string.deposit_and_monthly, article.deposit, article.rentFee)
    }
}
