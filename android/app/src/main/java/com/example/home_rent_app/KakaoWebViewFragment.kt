package com.example.home_rent_app

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.home_rent_app.databinding.FragmentKakaoWebViewBinding

class KakaoWebViewFragment : Fragment() {

    lateinit var binding: FragmentKakaoWebViewBinding
    private lateinit var navigator: NavController

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
                    BuildConfig.kakaoClientId +
                    "&redirect_uri=http://3.34.188.98:8080/login/oauth/callback&response_type=code"
            )
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        private fun checkUrl(request: WebResourceRequest?) {
            val url = request?.url ?: return
            val code = url.getQueryParameter(KAKAO_OAUTH_CODE_PARAM_KEY)

            if (url.scheme == KAKAO_OAUTH_REDIRECTION_SCHEME &&
                url.host == KAKAO_OAUTH_REDIRECTION_HOST &&
                code != null) {
                Log.d("WebViewCode in If", code)
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
        private const val KAKAO_OAUTH_REDIRECTION_HOST = "3.34.188.98"
        private const val KAKAO_OAUTH_CODE_PARAM_KEY = "code"
    }
}
