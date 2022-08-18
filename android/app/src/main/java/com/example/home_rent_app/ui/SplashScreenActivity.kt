package com.example.home_rent_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.home_rent_app.R
import com.example.home_rent_app.ui.viewmodel.LoginViewModel
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val content: View = findViewById(android.R.id.content)
        checkIsLogin(content)
    }

    private fun checkIsLogin(content: View) {
        collectStateFlow(viewModel.isLogin) { isLogin ->
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (isLogin) {
                            val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                            val intent =
                                Intent(this@SplashScreenActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            false
                        }
                    }
                }
            )
        }
    }
}
