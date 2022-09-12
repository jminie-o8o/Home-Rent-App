package com.example.home_rent_app.util

object Constants {
    const val BASE_URL = "http://3.38.191.0:80/"
    const val TOKEN_DATASTORE = "token_datastore"
    const val LOGIN_CHECK_DATASTORE = "login_check_datastore"
    const val USER_ID_DATASTORE = "user_id_datastore"
    const val DISPLAY_NAME_DATASTORE = "display_name_datastore"
    const val PROFILE_IMAGE_DATASTORE = "profile_image_datastore"
    const val GENDER_DATASTORE = "gender_datastore"
    const val GENDER_NEW = "new"
    const val GENDER_DEFAULT = "default"
    const val MAP_URL = "https://naveropenapi.apigw.ntruss.com/"
    const val REQ_GALLERY = 1
    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}
