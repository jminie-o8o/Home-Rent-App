package com.example.home_rent_app.ui.bookmark

import android.content.Intent
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
import com.example.home_rent_app.databinding.FragmentBookmarkWantHomeBinding
import com.example.home_rent_app.ui.bookmark.adapter.BookmarkWantHomeAdapter
import com.example.home_rent_app.ui.bookmark.viewmodel.BookmarkViewModel
import com.example.home_rent_app.ui.wanthome.detail.WantHomeDetailActivity
import com.example.home_rent_app.util.collectStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkWantHomeFragment : Fragment() {

    lateinit var binding: FragmentBookmarkWantHomeBinding
    lateinit var adapter: BookmarkWantHomeAdapter
    private val viewModel: BookmarkViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark_want_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private val goToDetail: (Int) -> Unit = {
        // 상세화면 이동
        val intent = Intent(requireContext(), WantHomeDetailActivity::class.java)
        intent.putExtra("homeId", it)
        startActivity(intent)
    }

    private val deleteBookmark: (Int) -> Unit = {
        viewModel.deleteWantHomeBookmark(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewScrollListener()
        adapter = BookmarkWantHomeAdapter(goToDetail, deleteBookmark)
        binding.rvBookmarkWantHome.adapter = adapter
        updateAdapter()
        deleteBookMarkToast()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWantHomeResultAtFirstPage()
    }

    private fun updateAdapter() {
        collectStateFlow(viewModel.wantHomeBookmarkResult) {
            adapter.submitList(it)
        }
    }

    private fun deleteBookMarkToast() {
        collectStateFlow(viewModel.deleteWantBookmarkStatusCode) { code ->
            if (code == 200) Toast.makeText(requireContext(), "관심목록에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setRecyclerViewScrollListener() {
        binding.rvBookmarkWantHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position

                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // RecyclerView Item의 개수
                // 스크롤이 끝에 도달했는지 확인
                if (lastVisibleItemPosition == itemTotalCount) {
                    // 다음 페이지 불러오기
                    viewModel.getWantHomeResult()
                }
            }
        })
    }
}
