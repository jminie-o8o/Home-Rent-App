package com.example.home_rent_app.data.repository.login

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.OAuthTokenResponse
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.ACCESS_TOKEN
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.LOGIN_CHECK
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.REFRESH_TOKEN
import com.example.home_rent_app.util.Constants.LOGIN_CHECK_DATASTORE
import com.example.home_rent_app.util.Constants.TOKEN_DATASTORE
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    @ApplicationContext private val context: Context
) : LoginRepository {

    override suspend fun getKakaoToken(kakaoOauthRequest: KakaoOauthRequest): OAuthTokenResponse {
        return loginApi.getKakaoToken(kakaoOauthRequest)
    }

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
    }

    private val Context.loginCheckDataStore by preferencesDataStore(LOGIN_CHECK_DATASTORE)
    private val Context.tokenDataStore by preferencesDataStore(TOKEN_DATASTORE)

    override suspend fun saveToken(token: List<String>) {
        context.tokenDataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.first()
            prefs[REFRESH_TOKEN] = token.last()
        }
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        context.loginCheckDataStore.edit { prefs ->
            prefs[LOGIN_CHECK] = true
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

    override suspend fun getIsLogin(): Flow<Boolean> {
        return context.loginCheckDataStore.data
            .map { prefs ->
                prefs[LOGIN_CHECK] ?: false
            }
    }
}
