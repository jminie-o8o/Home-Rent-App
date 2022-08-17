package com.example.home_rent_app.data.repository.loginProfile

import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.dto.toNickNameCheck
import com.example.home_rent_app.data.model.NickNameCheck
import javax.inject.Inject

class LoginProfileRepositoryImpl @Inject constructor(private val api: LoginApi) : LoginProfileRepository {

    override suspend fun checkNickName(nickName: String): NickNameCheck {
        return api.checkNickName(nickName).toNickNameCheck()
    }
}
