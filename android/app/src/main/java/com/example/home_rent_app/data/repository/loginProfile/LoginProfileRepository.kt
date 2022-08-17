package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.model.NickNameCheck

interface LoginProfileRepository {

    suspend fun checkNickName(nickName: String): NickNameCheck
}
