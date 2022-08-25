package com.example.home_rent_app.data

import com.example.home_rent_app.data.repository.refresh.RefreshRepository
import com.example.home_rent_app.data.repository.token.TokenRepository
import com.example.home_rent_app.util.AppSession
import com.example.home_rent_app.util.CoroutineException
import com.example.home_rent_app.util.logger
import io.getstream.chat.android.client.utils.toResult
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val appSession: AppSession,
    private val tokenRepository: TokenRepository,
    private val refreshRepository: RefreshRepository
) : Interceptor {

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

        val response = chain.proceed(requestBuilder.build())

        val ceh = CoroutineExceptionHandler { _, throwable ->
            logger("refresh token error : ${CoroutineException.checkThrowable(throwable).errorMessage}")
        }

        if (response.code == 401) {
            val refreshBuilder = chain.request()
                .newBuilder()
            runBlocking {
                CoroutineScope(Job() + ceh).launch {
                    val list = withContext(Dispatchers.IO) {
                        val token = refreshRepository.refreshToken()
                        listOf(token.accessToken.tokenCode, token.refreshToken.tokenCode)
                    }
                    tokenRepository.saveToken(list)
                    tokenRepository.setAppSession(list)
                    refreshBuilder.addHeader(
                        "access-token",
                        list[0]
                    ) // 추후 수정
                }.join()
            }
            return chain.proceed(refreshBuilder.build())
        }
        return response
    }

}
