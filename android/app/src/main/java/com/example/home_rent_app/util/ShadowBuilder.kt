package com.example.home_rent_app.util

import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import android.widget.ImageView

class ShadowBuilder(view: View) : View.DragShadowBuilder(view) {

    private val shadow = (view as ImageView).drawable

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
