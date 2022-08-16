package com.example.home_rent_app.util

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat

class MoneyFormat(private val editText: EditText): TextWatcher {

    private var result = ""

    private val decimalFormat = DecimalFormat("#,###")

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
            result = decimalFormat.format(charSequence.toString().replace(",","").toDouble())
            editText.setText(result);
            editText.setSelection(result.length);
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

}