package com.example.home_rent_app.data

import com.example.home_rent_app.util.AppSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutInterceptor @Inject constructor(
    private val appSession: AppSession
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val logoutRequest = request().newBuilder()
            .addHeader("access_token", appSession.jwt?.accessToken?.tokenCode.orEmpty())
            .addHeader("refresh_token", appSession.jwt?.refreshToken?.tokenCode.orEmpty())
            .build()
        proceed(logoutRequest)
    }
}
