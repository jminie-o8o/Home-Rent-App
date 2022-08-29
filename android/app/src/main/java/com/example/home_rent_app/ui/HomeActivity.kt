package com.example.home_rent_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityHomeBinding
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.User

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        binding.navigation.setupWithNavController(navHostFragment.navController)

    }

    fun goWantHomeActivity() {
        val intent = Intent(this, WantHomeActivity::class.java)
        startActivity(intent)
    }

    companion object User {

        val user = User(
            id = "1",
            name = "rest",
            image = "https://ifh.cc/g/cKhWxt.jpg"
        )
    }
}
