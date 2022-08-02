package com.example.home_rent_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.databinding.FragmentLoginBinding
import okhttp3.HttpUrl

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()
        binding.btnKakaoLogin.setOnClickListener {
            setClickButton(navigationController)
        }
    }

    private fun setClickButton(navController: NavController) {
        binding.btnKakaoLogin.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_kakaoWebViewFragment)
        }
    }

    private fun kakaoLogin() {
        val httpUrl = HttpUrl.Builder()
            .scheme("https")
            .host("kauth.kakao.com")
            .addPathSegment("oauth")
            .addPathSegment("authorize")
            .addQueryParameter("client_id", "9dc5e51153cd29428199781510c17a32")
            .addQueryParameter("redirect_uri", "http://3.34.188.98/login/oauth/callback")
            .addQueryParameter("response_type", "code")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(httpUrl.toString()))
        startActivity(intent)
    }
}
