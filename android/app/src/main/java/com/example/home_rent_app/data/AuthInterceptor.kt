package com.example.home_rent_app.data

import com.example.home_rent_app.util.AppSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val appSession: AppSession
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = appSession.jwt

        val requestBuilder = chain.request()
            .newBuilder()

        jwt?.let {
            requestBuilder.addHeader(
                "Authorization",
                "${it.accessToken.tokenCode} ${it.refreshToken.tokenCode}"
            ) // 추후 수정
        }

        return chain.proceed(requestBuilder.build())
    }
}
