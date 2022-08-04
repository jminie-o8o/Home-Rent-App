package com.example.home_rent_app.data.repository.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.home_rent_app.data.api.LoginApi
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.data.model.OAuthTokenResponse
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.ACCESS_TOKEN
import com.example.home_rent_app.data.repository.login.LoginRepositoryImpl.PreferenceKeys.REFRESH_TOKEN
import com.example.home_rent_app.util.Constants.DATASTORE_NAME
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
    }

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    override suspend fun saveToken(token: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.first()
            prefs[REFRESH_TOKEN] = token.last()
        }
    }

    override suspend fun getToken(): Flow<List<String>> {
        return context.dataStore.data
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
}
