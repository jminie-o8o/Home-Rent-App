package com.example.home_rent_app.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityLoginBinding
import com.example.home_rent_app.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val expire = intent.getBooleanExtra("expire", false)
        if (expire) {
//            finishAffinity()
            Toast.makeText(this, "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    // EditText 이외에 다른 곳을 터치하면 소프트 키보드 내려가는 기능
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    fun moveToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {

        private const val EXPIRE = true

        fun newInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("expire", EXPIRE)
        }
    }
}
