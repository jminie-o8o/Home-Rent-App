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
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.BuildConfig
import com.example.home_rent_app.R
import com.example.home_rent_app.data.model.NaverOauthRequest
import com.example.home_rent_app.databinding.FragmentNaverWebViewBinding
import com.example.home_rent_app.ui.viewmodel.LoginViewModel
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaverWebViewFragment : Fragment() {

    lateinit var binding: FragmentNaverWebViewBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_naver_web_view, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()
        binding.kakaoWebView.run {
            webViewClient = CustomWebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(
                "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" +
                    BuildConfig.naverClientId +
                    "&redirect_uri=http://54.180.8.0:8080/login/oauth/callback&state=tany1004"
            )
        }
        setupObserveAndMove(navigationController)
    }

    private fun setupObserveAndMove(navController: NavController) {
        collectStateFlow(viewModel.accessToken) { accessToken ->
            if (!accessToken.isNullOrEmpty()) {
                navController.navigate(R.id.action_naverWebViewFragment_to_loginProfileFragment)
            }
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        private fun checkUrl(request: WebResourceRequest?) {
            val url = request?.url ?: return
            val code = url.getQueryParameter(NAVER_OAUTH_CODE_PARAM_KEY)

            if (url.scheme == NAVER_OAUTH_REDIRECTION_SCHEME &&
                url.host == NAVER_OAUTH_REDIRECTION_HOST &&
                code != null
            ) {
                viewModel.getNaverToken(NaverOauthRequest(code.toString()))
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
        private const val NAVER_OAUTH_REDIRECTION_SCHEME = "http"
        private const val NAVER_OAUTH_REDIRECTION_HOST = "54.180.8.0"
        private const val NAVER_OAUTH_CODE_PARAM_KEY = "code"
    }
}
