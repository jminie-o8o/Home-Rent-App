package com.example.home_rent_app.data

import com.example.home_rent_app.data.session.AppSession
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshInterceptor @Inject constructor(
    private val appSession: AppSession
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = appSession.jwt

        val requestBuilder = chain.request()
            .newBuilder()

        jwt?.let {
            requestBuilder.addHeader(
                "access-token",
                it.accessToken.tokenCode
            ).addHeader(
                "refresh-token",
                it.refreshToken.tokenCode
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}
