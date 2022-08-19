package com.example.home_rent_app.ui.wanthome.step2

import android.annotation.SuppressLint
import android.app.Dialog
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeSecondStepBinding
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WantHomeSecondStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeSecondStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_want_home_second_step,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()
        goHomeActivity()
        register(navigationController)
    }


    private fun goHomeActivity() {
        binding.btnClose.setOnClickListener {
            val activity = activity as WantHomeActivity
            activity.goHomeActivity()
        }
    }

    private fun register(navController: NavController) {
        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_wantHomeSecondStepFragment_to_wantHomeDetailFragment)
        }
    }
}
