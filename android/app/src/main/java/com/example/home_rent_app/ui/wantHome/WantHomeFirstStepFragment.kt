package com.example.home_rent_app.ui.wantHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeFirstStepBinding
import com.example.home_rent_app.ui.WantHomeActivity

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
        goBack()
    }

    private fun goBack() {
        binding.btnClose.setOnClickListener {
            val activity = activity as WantHomeActivity
            activity.onBackPressed()
        }
    }

    fun showDatePicker() {

    }
}
