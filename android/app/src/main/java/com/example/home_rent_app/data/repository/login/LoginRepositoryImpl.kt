package com.example.home_rent_app.data.repository.login

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.dto.toJWT
import com.example.home_rent_app.data.dto.toUser
import com.example.home_rent_app.data.model.AccessToken
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.data.model.RefreshToken
import com.example.home_rent_app.data.model.User
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.ACCESS_TOKEN
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.LOGIN_CHECK
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.PROFILE_IMAGE
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.REFRESH_TOKEN
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.USER_ID
import com.example.home_rent_app.ui.HomeActivity.User.user
import com.example.home_rent_app.util.AppSession
import com.example.home_rent_app.util.Constants.LOGIN_CHECK_DATASTORE
import com.example.home_rent_app.util.Constants.PROFILE_IMAGE_DATASTORE
import com.example.home_rent_app.util.Constants.TOKEN_DATASTORE
import com.example.home_rent_app.util.Constants.USER_ID_DATASTORE
import com.example.home_rent_app.util.logger
import dagger.hilt.android.qualifiers.ApplicationContext
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.enqueue
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.ConnectionData
import io.getstream.chat.android.client.utils.onErrorSuspend
import io.getstream.chat.android.client.utils.onSuccessSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val appSession: AppSession,
    private val chatClient: ChatClient,
    @ApplicationContext private val context: Context
) :   LoginRepository {

    override suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): JWT {
        return loginApi.getKakaoToken(kakaoOauthRequest).toJWT()
    }

    override suspend fun getNaverToken(naverOauthRequest: NaverOauthRequest): JWT {
        return loginApi.getNaverToken(naverOauthRequest).toJWT()
    }

    override suspend fun getKakaoUser(kakaoOauthRequest: KakaoOauthRequest): User {
        return loginApi.getKakaoToken(kakaoOauthRequest).toUser()
    }

    override suspend fun getNaverUser(naverOauthRequest: NaverOauthRequest): User {
        return loginApi.getNaverToken(naverOauthRequest).toUser()
    }

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
        val USER_ID = intPreferencesKey("user_id")
        val PROFILE_IMAGE = stringPreferencesKey("profile_image")
    }

    private val Context.loginCheckDataStore by preferencesDataStore(LOGIN_CHECK_DATASTORE)
    private val Context.tokenDataStore by preferencesDataStore(TOKEN_DATASTORE)
    private val Context.userIdDataStore by preferencesDataStore(USER_ID_DATASTORE)
    private val Context.profileImageDataStore by preferencesDataStore(PROFILE_IMAGE_DATASTORE)

    override suspend fun saveToken(token: List<String>) {
        if (token.isNotEmpty()) {
            context.tokenDataStore.edit { prefs ->
                prefs[ACCESS_TOKEN] = token.first()
                prefs[REFRESH_TOKEN] = token.last()
            }
        }
    }

    override suspend fun getToken(): Flow<List<String>> {
        return context.tokenDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { prefs ->
                prefs.asMap().values.toList().map {
                    it.toString()
                }
            }
    }

    override suspend fun saveIsLogin() {
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        context.loginCheckDataStore.edit { prefs ->
            prefs[LOGIN_CHECK] = true
        }
    }

    override fun setAppSession(token: List<String>) {
        val accessToken = AccessToken(token[0])
        val refreshToken = RefreshToken(token[1])
        logger("accessToken : $accessToken ")
        logger("refreshToken : $refreshToken ")
        appSession.jwt = JWT(accessToken, refreshToken)
    }

    override suspend fun saveUserID(userId: Int) {
        context.userIdDataStore.edit { prefs ->
            prefs[USER_ID] = userId
        }
    }

    override suspend fun saveProfileImage(image: String) {
        context.profileImageDataStore.edit { prefs ->
            prefs[PROFILE_IMAGE] = image
        }
    }

    override suspend fun getIsLogin(): Flow<Boolean> {
        return context.loginCheckDataStore.data
            .map { prefs ->
                prefs[LOGIN_CHECK] ?: false
            }
    }

    override fun connectUser() = flow {
        // disconnect a user if already connected.
        disconnectUser()
        val token = chatClient.devToken(user.id)

        val result = chatClient.connectUser(user, token).await()

        result.onSuccessSuspend {
            emit(result.data())
        }
        result.onErrorSuspend {
            logger("Connect fail : ${it.message}")
        }
    }

    private fun disconnectUser() {
        val currentUser = chatClient.getCurrentUser()
        if (currentUser != null && user.id == currentUser.id) {
            chatClient.disconnect(true).execute()
        }
    }
}
