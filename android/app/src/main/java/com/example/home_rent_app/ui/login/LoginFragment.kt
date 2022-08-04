package com.example.home_rent_app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        setClickButton(navigationController)
    }

    private fun setClickButton(navController: NavController) {
        binding.btnKakaoLogin.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_kakaoWebViewFragment)
        }
        binding.btnNaverLogin.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_loginProfileFragment)
        }
    }
}
