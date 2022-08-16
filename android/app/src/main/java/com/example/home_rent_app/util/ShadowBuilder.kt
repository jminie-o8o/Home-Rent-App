package com.example.home_rent_app.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View

class ShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {

        val width: Int = view.width / 2

        val height: Int = view.height / 2

        shadow.setBounds(0, 0, width, height)

        size.set(width, height)

        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
        shadow.draw(canvas)
    }

}