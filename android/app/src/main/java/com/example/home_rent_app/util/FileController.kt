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
import java.io.File
import java.io.FileOutputStream
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
        logger("result ; $result")
        val file = File(result)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }

    // ACTION_GET_CONTENT를 사용할때 써야하는 방식
    fun uriToMulti(imageUri: Uri): MultipartBody.Part {

        val uri = imageUri.path.orEmpty()
        logger("uri : $uri")
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
        val path = tempFile.absolutePath
        val file = File(path)
        logger("path : ${path} fileName:  ${fileName} file.name : ${file.name}")
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("images", file.name, requestFile)
    }

}
