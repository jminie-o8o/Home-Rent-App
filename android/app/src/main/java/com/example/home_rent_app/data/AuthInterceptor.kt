package com.example.home_rent_app.data

import com.example.home_rent_app.data.repository.refresh.RefreshRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.data.session.AppSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val appSession: AppSession,
    private val tokenRepository: TokenRepository,
    private val refreshRepository: RefreshRepository
) : Interceptor, Authenticator {

    override fun intercept(chain: Interceptor.Chain): Response {

        val jwt = appSession.jwt

        val requestBuilder = chain.request()
            .newBuilder()

        jwt?.let {
            requestBuilder.addHeader(
                "access-token",
                it.accessToken.tokenCode
            )
        }

        return chain.proceed(requestBuilder.build())
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val list = runBlocking(Dispatchers.IO) {
            val jwt = refreshRepository.refreshToken()
            val tokenCode = jwt?.let {
                val tokenCode = listOf(jwt.accessToken.tokenCode, jwt.refreshToken.tokenCode)
                tokenRepository.saveToken(tokenCode)
                tokenRepository.setAppSession(tokenCode)
                tokenCode
            }
            tokenCode
        } ?: return null

        return response.request
            .newBuilder()
            .removeHeader("access-token")
            .addHeader("access-token", list[0])
            .build()
    }
}
