package com.example.home_rent_app.util

import android.content.ContentResolver
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun getBitmap(contentResolver: ContentResolver, uri: Uri) = if (Build.VERSION.SDK_INT < 28) {
    MediaStore.Images.Media.getBitmap(
        contentResolver,
        uri
    )
} else {
    val source = ImageDecoder.createSource(
        contentResolver,
        uri
    )
    ImageDecoder.decodeBitmap(source)
}
