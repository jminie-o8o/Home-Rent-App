package com.example.home_rent_app

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(BuildConfig.naverMapId)
    }
}
