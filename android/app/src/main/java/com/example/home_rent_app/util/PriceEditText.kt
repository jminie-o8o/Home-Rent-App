package com.example.home_rent_app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.home_rent_app.R

class PriceEditText(context: Context, attr: AttributeSet): AppCompatEditText(context, attr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 40f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val width = (measuredWidth / 10 * 8).toFloat()
        val height = (measuredHeight / 1.7).toFloat()

        canvas?.drawText(context.getString(R.string.price_measure),width, height, paint)
    }
}