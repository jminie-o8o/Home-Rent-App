package com.example.home_rent_app.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.home_rent_app.R
import java.text.DecimalFormat

class MoneyFormat(private val editText: EditText) : TextWatcher {

    private var result = ""

    private val decimalFormat = DecimalFormat("#,###")

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result) {
            result = decimalFormat.format(charSequence.toString().replace(",", "").toDouble())
            editText.setText(result)
            editText.setSelection(result.length)
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}

@BindingAdapter("deposit", "rentFee")
fun TextView.setDepositAndFee(deposit: Int?, fee: Int?) {
    logger("setDepositAndFee $deposit , $fee")
    if (deposit == null && fee == null) {
        return
    }
    text = if (fee == 0) {
        context.getString(R.string.deposit, deposit)
    } else {
        context.getString(R.string.deposit_and_monthly, deposit, fee)
    }
}

private fun convertMoney(price: Int): String {
    var temp = price
    for (i in 0..3) {
        if (temp / 10 == 0) {
            return "${temp}원"
        }
        if (temp / 10 != 0) {
            temp /= 10
        }
    }
    return "${temp}만원"
}

@BindingAdapter("maintenanceFee")
fun setMaintenanceFee(textView: TextView, maintenanceFee: Int?) {
    maintenanceFee?.let {
        textView.text = convertMoney(maintenanceFee)
    }
}
