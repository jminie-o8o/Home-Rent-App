package com.example.home_rent_app.util

import com.example.home_rent_app.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var user: User? = null
}
