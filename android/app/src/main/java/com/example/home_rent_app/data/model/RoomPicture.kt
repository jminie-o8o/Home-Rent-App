package com.example.home_rent_app.data.model

import android.net.Uri
import java.io.Serializable

data class RoomPicture(
    val id: Int,
    val uri: Uri,
    var isMain: Boolean = false
) : Serializable
