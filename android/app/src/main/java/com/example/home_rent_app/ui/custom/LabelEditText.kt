package com.example.home_rent_app.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.home_rent_app.R

class LabelEditText(context: Context, attr: AttributeSet) : AppCompatEditText(context, attr) {

    private lateinit var label: String
    private var position = 10
    init {
        val typedArr = context.obtainStyledAttributes(attr, R.styleable.PriceEditText)
        // format을 구분하여 속성값 참조
        label = typedArr.getString(R.styleable.PriceEditText_labelText).orEmpty()
        position = typedArr.getInt(R.styleable.PriceEditText_position, 10)
        typedArr.recycle()
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 40f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 작으면 왼쪽 크면 오른쪽
        val width = (measuredWidth / position * 8).toFloat()
        val height = (measuredHeight / 1.7).toFloat()

        canvas?.drawText(label, width, height, paint)
    }
}
