package com.example.home_rent_app.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.home_rent_app.R
import com.example.home_rent_app.ui.viewmodel.LoginViewModel
import com.example.home_rent_app.util.LoginCheck
import com.example.home_rent_app.util.collectLatestStateFlow
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        logger("스플래시 시작")
        installSplashScreen()
        super.onCreate(savedInstanceState)
        logger("스플래시 중간")
        setContentView(R.layout.activity_splash_screen)
        val content: View = findViewById(android.R.id.content)
        checkIsLogin(content)
        viewModel.checkLogin()
        logger("스플래시")
    }

    private fun checkIsLogin(content: View) {
        collectLatestStateFlow(viewModel.isLogin) { isLogin ->
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return when (isLogin) {
                            true -> {
                                logger("isLogin HomeActivity: $isLogin")
                                val intent =
                                    Intent(this@SplashScreenActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                                content.viewTreeObserver.removeOnPreDrawListener(this)
                                false
                            }
                            false -> {
                                logger("isLogin LoginActivity: $isLogin")
                                val intent =
                                    Intent(this@SplashScreenActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                                content.viewTreeObserver.removeOnPreDrawListener(this)
                                false
                            }
                        }
                    }
                }
            )
        }
    }
}

