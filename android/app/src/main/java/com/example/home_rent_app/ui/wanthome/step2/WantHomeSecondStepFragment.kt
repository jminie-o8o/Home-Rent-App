package com.example.home_rent_app.ui.wanthome.step2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentWantHomeSecondStepBinding
import com.example.home_rent_app.ui.viewmodel.WantHomeViewModel
import com.example.home_rent_app.ui.wanthome.WantHomeActivity
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WantHomeSecondStepFragment : Fragment() {

    lateinit var binding: FragmentWantHomeSecondStepBinding
    private val viewModel: WantHomeViewModel by activityViewModels()

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
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()
        goHomeActivity()
        register(navigationController)
        collectStateFlow(viewModel.location) {
            Log.d("지역", it.name)
        }
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
