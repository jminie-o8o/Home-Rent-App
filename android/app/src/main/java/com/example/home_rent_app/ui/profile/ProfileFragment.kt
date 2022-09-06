package com.example.home_rent_app.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentProfileBinding
import com.example.home_rent_app.ui.HomeActivity
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.collectLatestStateFlow
import com.example.home_rent_app.util.logger
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()
    @Inject
    lateinit var userSession: UserSession

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = profileViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpProfileTab.adapter = ProfileViewPagerAdapter(activity as HomeActivity)
        val navigationController = findNavController()
        TabLayoutMediator(binding.tlProfileTab, binding.vpProfileTab) { tab, position ->
            when (position) {
                0 -> tab.text = "양도해요"
                1 -> tab.text = "양수해요"
            }
        }.attach()
        goToModifyProfile(navigationController)
        observeDeleteMessage(requireActivity().applicationContext)
        observeProfileModifyChange(requireActivity().applicationContext)
    }

    private fun goToModifyProfile(navController: NavController) {
        binding.btnModifyProfile.setOnClickListener {
            navController.navigate(
                R.id.action_profileFragment_to_profileModifyFragment
            )
        }
    }

    private fun observeDeleteMessage(context: Context) {
        collectLatestStateFlow(profileViewModel.deleteMessage) {
            Toast.makeText(context, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeProfileModifyChange(context: Context) {
        collectLatestStateFlow(profileViewModel.profileModifyMessage) {
            Toast.makeText(context, "프로필이 변경되었습니다..", Toast.LENGTH_SHORT).show()
        }
    }
}
