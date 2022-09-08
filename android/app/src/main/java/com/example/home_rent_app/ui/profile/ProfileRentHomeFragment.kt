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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.data.session.ItemIdSession
import com.example.home_rent_app.data.session.UserSession
import com.example.home_rent_app.databinding.FragmentProfileRentHomeBinding
import com.example.home_rent_app.ui.home.HomeActivity
import com.example.home_rent_app.ui.profile.adapter.ProfileGiveHomeAdapter
import com.example.home_rent_app.ui.profile.viewmodel.ProfileViewModel
import com.example.home_rent_app.util.collectStateFlow
import com.example.home_rent_app.util.logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileRentHomeFragment : Fragment() {

    lateinit var binding: FragmentProfileRentHomeBinding
    lateinit var adapter: ProfileGiveHomeAdapter
    @Inject
    lateinit var userSession: UserSession
    @Inject
    lateinit var idSession: ItemIdSession
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_rent_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewScrollListener()
        adapter = ProfileGiveHomeAdapter(viewModel, idSession, requireContext())
        binding.rvProfileGiveHome.adapter = adapter
        updateAdapter()
        logout()
        observeMessage(requireActivity().applicationContext)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGiveHomeProfileAtFirstPage()
    }

    private fun updateAdapter() {
        collectStateFlow(viewModel.giveHomeProfileResult) {
            adapter.submitList(it)
        }
    }

    private fun setRecyclerViewScrollListener() {
        binding.rvProfileGiveHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position

                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // RecyclerView Item의 개수
                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    // 다음 페이지 불러오기
                    logger("다음 페이지 불러오기")
                    viewModel.getGiveHomeProfile()
                }
            }
        })
    }

    private fun logout() {
        binding.tvProfileGiveHomeLogout.setOnClickListener {
            viewModel.logout()
            val activity = activity as HomeActivity
            activity.goLoginActivityWithLogout()
        }
    }

    private fun observeMessage(context: Context) {
        collectStateFlow(viewModel.logoutMessage) {
            Toast.makeText(context, "성공적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
