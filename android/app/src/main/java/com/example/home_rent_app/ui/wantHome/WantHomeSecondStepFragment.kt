package com.example.home_rent_app.ui.wantHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeSecondStepBinding
import com.example.home_rent_app.ui.WantHomeActivity

class WantHomeSecondStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeSecondStepBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_want_home_second_step, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goHomeActivity()
    }

    private fun goHomeActivity() {
        binding.btnClose.setOnClickListener {
            val activity = activity as WantHomeActivity
            activity.goHomeActivity()
        }
    }
}
