package com.example.home_rent_app.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.KakaoOauthRequest
import com.example.home_rent_app.databinding.FragmentKakaoWebViewBinding
import com.example.home_rent_app.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoWebViewFragment : Fragment() {

    lateinit var binding: FragmentKakaoWebViewBinding
    private lateinit var navigator: NavController
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_kakao_web_view, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigation.findNavController(view)
        binding.kakaoWebView.run {
            webViewClient = CustomWebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(
                "https://kauth.kakao.com/oauth/authorize?client_id=" +
                    BuildConfig.kakaoCientId +
                    "&redirect_uri=http://54.180.8.0:8080/login/oauth/callback&response_type=code"
            )
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        private fun checkUrl(request: WebResourceRequest?) {
            val url = request?.url ?: return
            val code = url.getQueryParameter(KAKAO_OAUTH_CODE_PARAM_KEY)

            if (url.scheme == KAKAO_OAUTH_REDIRECTION_SCHEME &&
                url.host == KAKAO_OAUTH_REDIRECTION_HOST &&
                code != null
            ) {
                viewModel.getKakaoToken(KakaoOauthRequest(code.toString()))
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            checkUrl(request)
            return super.shouldOverrideUrlLoading(view, request)
        }
    }

    companion object {
        private const val KAKAO_OAUTH_REDIRECTION_SCHEME = "http"
        private const val KAKAO_OAUTH_REDIRECTION_HOST = "54.180.8.0"
        private const val KAKAO_OAUTH_CODE_PARAM_KEY = "code"
    }
}
