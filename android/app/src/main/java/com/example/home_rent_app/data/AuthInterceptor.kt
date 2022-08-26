package com.example.home_rent_app.data

import com.example.home_rent_app.data.repository.refresh.RefreshRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.util.AppSession
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.logger
import io.getstream.chat.android.client.utils.toResult
import kotlinx.coroutines.*
import okhttp3.*
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
            ) // 추후 수정
        }

        return chain.proceed(requestBuilder.build())
    }

    override fun authenticate(route: Route?, response: Response): Request {
        val list = runBlocking(Dispatchers.IO) {
            val jwt = refreshRepository.refreshToken()
            val tokenCode = listOf(jwt.accessToken.tokenCode, jwt.refreshToken.tokenCode)
            tokenRepository.saveToken(tokenCode)
            tokenRepository.setAppSession(tokenCode)
            tokenCode
        }
        return response.request
            .newBuilder()
            .removeHeader("access-token")
            .addHeader("access-token", list[0])
            .build()
    }

}
