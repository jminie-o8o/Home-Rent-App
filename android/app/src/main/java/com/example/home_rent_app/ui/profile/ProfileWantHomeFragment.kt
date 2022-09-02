package com.example.home_rent_app.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentProfileWantHomeBinding
import com.example.home_rent_app.ui.HomeActivity
import com.example.home_rent_app.util.ItemIdSession
import com.example.home_rent_app.util.UserSession
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileWantHomeFragment : Fragment() {

    lateinit var binding: FragmentProfileWantHomeBinding
    lateinit var adapter: ProfileWantHomeAdapter
    @Inject
    lateinit var userSession: UserSession
    @Inject
    lateinit var idSession: ItemIdSession
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_want_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewScrollListener()
        adapter = ProfileWantHomeAdapter(viewModel, idSession, requireContext())
        binding.rvProfileWantHome.adapter = adapter
        updateAdapter()
        viewModel.getWantHomeProfile(userSession.userId ?: 0)
        logout()
    }

    private fun updateAdapter() {
        collectStateFlow(viewModel.wantHomeProfileResult) {
            adapter.submitList(it)
        }
    }

    private fun setRecyclerViewScrollListener() {
        binding.rvProfileWantHome.addOnScrollListener( object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position

                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // RecyclerView Item의 개수
                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    // 다음 페이지 불러오기
                    userSession.userId?.let { viewModel.getWantHomeProfile(it) }
                }
            }
        })
    }

    private fun logout() {
        binding.tvProfileWantHomeLogout.setOnClickListener {
            viewModel.logout()
            val activity = activity as HomeActivity
            activity.goLoginActivityWithLogout()
        }
    }
}
