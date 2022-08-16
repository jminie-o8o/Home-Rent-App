package com.example.home_rent_app.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class MainImageView(context: Context, attributeSet: AttributeSet) :
    AppCompatImageView(context, attributeSet) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(160, 0,0,0)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 60f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val width = measuredWidth
        val height = measuredHeight

        val textX = (width / 2.5).toFloat()
        val textY = (height * 0.9).toFloat()

        val rectHeight = height * 0.7

        canvas?.drawRect(0f, rectHeight.toFloat(), width.toFloat(), height.toFloat(), backgroundPaint)
        canvas?.drawText("대표사진", textX, textY, textPaint)
    }
}