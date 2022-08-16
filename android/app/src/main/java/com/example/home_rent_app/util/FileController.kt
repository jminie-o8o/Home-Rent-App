package com.example.home_rent_app.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileController @Inject constructor(@ApplicationContext private val context: Context) {

    fun uriToMultiPart(imageUri : Uri): MultipartBody.Part {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver?.query(imageUri, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow("_data") ?: -1
        c?.moveToFirst()
        val result = c?.getString(index).orEmpty()
        c?.close()
        logger("result : $result selectedImageUri : $imageUri index: $index selectedImageUri.path : ${imageUri.path}")

        val uri = imageUri.path.orEmpty()

        val file = File(result)
        logger("file.name : ${file.name}")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }

    fun uriToMulti(imageUri: Uri): MultipartBody.Part {
        val uri = imageUri.path.orEmpty()
        val fileName = uri.split("/").last()
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File(storageDir, fileName)
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(tempFile)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
        val file = File(tempFile.absolutePath)
        logger("uri : ${uri} file.name : ${file.name} file path: ${file.absolutePath} imageUri.encodedPath : ${imageUri.encodedPath}")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }

    fun uriToPart(imageUri: Uri): MultipartBody.Part {
        val file = File(imageUri.toString())
        logger("imageUri : $imageUri file.name : ${file.name} file path: ${file.absolutePath} imageUri.encodedPath : ${imageUri.encodedPath}")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }
}