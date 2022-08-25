package com.example.home_rent_app.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var userId: Int? = null
}
