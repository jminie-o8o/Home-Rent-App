package com.example.home_rent_app.ui.wantHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeFirstStepBinding
import com.example.home_rent_app.ui.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

class WantHomeFirstStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeFirstStepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_want_home_first_step, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    private fun hideBottomNavigation(boolean: Boolean = true) {
        val homeActivity = activity as HomeActivity
        homeActivity.hideBottomNavigationView(boolean)
    }
}
