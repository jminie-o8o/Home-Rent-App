package com.example.home_rent_app.ui

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityHomeBinding
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.util.AppSession
import com.example.home_rent_app.util.UserSession
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.User
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    @Inject
    lateinit var userSession: UserSession
    @Inject
    lateinit var appSession: AppSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        binding.navigation.setupWithNavController(navHostFragment.navController)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    fun goWantHomeActivity() {
        val intent = Intent(this, WantHomeActivity::class.java)
        startActivity(intent)
    }

    fun goLoginActivityWithLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goBack() {
        onBackPressed()
    }
}
