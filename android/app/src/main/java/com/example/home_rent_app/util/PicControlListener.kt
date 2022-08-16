package com.example.home_rent_app.util

interface PicControlListener {

    fun removePic(position: Int)

    fun changePic(beforePosition: Int, targetPosition: Int)
}