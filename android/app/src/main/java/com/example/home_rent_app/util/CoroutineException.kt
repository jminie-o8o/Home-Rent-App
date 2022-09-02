package com.example.home_rent_app.util

import com.example.home_rent_app.data.model.CEHModel
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

object CoroutineException {

    fun checkThrowable(throwable: Throwable): CEHModel {
        return when (throwable) {
            is SocketException -> CEHModel(throwable, "소켓 연결이 끊겼습니다.")
            is HttpException -> {
                CEHModel(throwable, "Http 관련 오류입니다")
            }
            is UnknownHostException -> CEHModel(throwable, "UnknownHost 오류입니다.")
            is ConnectException -> CEHModel(throwable, "네트워크 연결이 끊겼습니다.")
            else -> CEHModel(throwable, "알 수 없는 오류입니다.")
        }
    }

}
