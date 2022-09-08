package com.example.home_rent_app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.home_rent_app.data.model.AccessToken
import com.example.home_rent_app.data.model.JWT
import com.example.home_rent_app.data.model.RefreshToken
import com.example.home_rent_app.data.session.AppSession
import com.example.home_rent_app.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStore @Inject constructor(
    private val appSession: AppSession,
    @ApplicationContext private val context: Context
) {

    object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val LOGIN_CHECK = booleanPreferencesKey("login_check")
        val USER_ID = intPreferencesKey("user_id")
        val DISPLAY_NAME = stringPreferencesKey("display_name")
        val PROFILE_IMAGE = stringPreferencesKey("profile_image")
        val GENDER = stringPreferencesKey("gender")
    }

    private val Context.loginCheckDataStore by preferencesDataStore(Constants.LOGIN_CHECK_DATASTORE)
    private val Context.tokenDataStore by preferencesDataStore(Constants.TOKEN_DATASTORE)
    private val Context.userIdDataStore by preferencesDataStore(Constants.USER_ID_DATASTORE)
    private val Context.displayNameDataStore by preferencesDataStore(Constants.DISPLAY_NAME_DATASTORE)
    private val Context.profileImageDataStore by preferencesDataStore(Constants.PROFILE_IMAGE_DATASTORE)
    private val Context.genderDataStore by preferencesDataStore(Constants.GENDER_DATASTORE)

    suspend fun saveToken(token: List<String>) {
        if (token.isNotEmpty()) {
            context.tokenDataStore.edit { prefs ->
                prefs[PreferenceKeys.ACCESS_TOKEN] = token.first()
                prefs[PreferenceKeys.REFRESH_TOKEN] = token.last()
            }
        }
    }

    suspend fun getToken(): Flow<List<String>> {
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

    fun setAppSession(token: List<String>) {
        val accessToken = AccessToken(token[0])
        val refreshToken = RefreshToken(token[1])
        appSession.jwt = JWT(accessToken, refreshToken)
    }

    suspend fun saveIsLogin() {
        // AccessToken, RefreshToken 이 제대로 들어온 여부를 확인하는 boolean 값
        context.loginCheckDataStore.edit { prefs ->
            prefs[PreferenceKeys.LOGIN_CHECK] = true
        }
    }

    fun getIsLogin(): Flow<Boolean> {
        return context.loginCheckDataStore.data
            .map { prefs ->
                prefs[PreferenceKeys.LOGIN_CHECK] ?: false
            }
    }

    suspend fun saveUserID(userId: Int) {
        context.userIdDataStore.edit { prefs ->
            prefs[PreferenceKeys.USER_ID] = userId
        }
    }

    suspend fun saveDisplayName(displayName: String) {
        context.displayNameDataStore.edit { prefs ->
            prefs[PreferenceKeys.DISPLAY_NAME] = displayName
        }
    }

    suspend fun saveProfileImage(image: String) {
        context.profileImageDataStore.edit { prefs ->
            prefs[PreferenceKeys.PROFILE_IMAGE] = image
        }
    }

    suspend fun saveGender(gender: String) {
        context.genderDataStore.edit { prefs ->
            prefs[PreferenceKeys.GENDER] = gender
        }
    }

    fun getUserId() = context.userIdDataStore.data

    fun getDisplayName() = context.displayNameDataStore.data

    fun getProfileImage() = context.profileImageDataStore.data

    fun getGender() = context.genderDataStore.data

    suspend fun clearDataStore() {
        context.loginCheckDataStore.edit { it.clear() }
        context.tokenDataStore.edit { it.clear() }
        context.userIdDataStore.edit { it.clear() }
        context.displayNameDataStore.edit { it.clear() }
        context.profileImageDataStore.edit { it.clear() }
        context.genderDataStore.edit { it.clear() }
    }
}
