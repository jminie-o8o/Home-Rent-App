package com.example.home_rent_app.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.home_rent_app.R
import com.example.home_rent_app.ui.home.HomeActivity
import com.example.home_rent_app.ui.login.LoginActivity
import com.example.home_rent_app.ui.login.viewmodel.LoginViewModel
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
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
        logger("SplashScreenActivity onCreate")
    }

    override fun onStart() {
        super.onStart()
        logger("SplashScreenActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        logger("SplashScreenActivity onResume")
    }

    override fun onRestart() {
        super.onRestart()
        logger("SplashScreenActivity onRestart")
    }

    override fun onStop() {
        super.onStop()
        logger("SplashScreenActivity onStop")
    }

    override fun onPause() {
        super.onPause()
        logger("SplashScreenActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger("SplashScreenActivity onDestroy")
    }

    private fun checIsLogin(content: View) {
        collectStateFlow(viewModel.isLogin) { isLogin ->
            when (isLogin) {
                true -> {
                    logger("isLogin HomeActivity: $isLogin")
                    val intent =
                        Intent(this@SplashScreenActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
                false -> {
                    logger("isLogin LoginActivity: $isLogin")
                    val intent =
                        Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }
    }


    private fun checkIsLogin(content: View) {
        collectStateFlow(viewModel.isLogin) { isLogin ->
            content.viewTreeObserver.addOnGlobalLayoutListener (
                object : ViewTreeObserver.OnGlobalLayoutListener {
//                    override fun onPreDraw(): Boolean {
//                        return when (isLogin) {
//                            true -> {
//                                logger("isLogin HomeActivity: $isLogin")
//                                val intent =
//                                    Intent(this@SplashScreenActivity, HomeActivity::class.java)
//                                startActivity(intent)
//                                finishAffinity()
//                                content.viewTreeObserver.removeOnPreDrawListener(this)
//                                false
//                            }
//                            false -> {
//                                logger("isLogin LoginActivity: $isLogin")
//                                val intent =
//                                    Intent(this@SplashScreenActivity, LoginActivity::class.java)
//                                startActivity(intent)
//                                finishAffinity()
//                                content.viewTreeObserver.removeOnPreDrawListener(this)
//                                false
//                            }
//                        }
//                    }

                    override fun onGlobalLayout() {
                        when (isLogin) {
                            true -> {
                                logger("isLogin HomeActivity: $isLogin")
                                val intent =
                                    Intent(this@SplashScreenActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                                content.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            }
                            false -> {
                                logger("isLogin LoginActivity: $isLogin")
                                val intent =
                                    Intent(this@SplashScreenActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                                content.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            }
                        }
                    }
                }
            )
        }
    }
}

