package com.example.home_rent_app.util

import com.example.home_rent_app.data.model.JWT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSession @Inject constructor() {
    var jwt: JWT? = null
}
