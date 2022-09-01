package com.example.home_rent_app.ui.home

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.FragmentHomeBinding
import com.example.home_rent_app.ui.HomeActivity
import com.example.home_rent_app.ui.detail.DetailRentActivity
import com.example.home_rent_app.ui.findroom.FindHomeActivity
import com.example.home_rent_app.ui.transfer.TransferActivity
import com.example.home_rent_app.ui.wanthomeresult.WantHomeResultActivity
import kotlinx.coroutines.NonCancellable.start

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private var isFabOpen = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goWantHomeActivity()
    }

    private fun goWantHomeActivity() {
        val wantedEmoji = String(Character.toChars(0x1F3E0))
        val rentEmoji = String(Character.toChars(0x1F91D))
        binding.tvBtnWanted.text = String.format(getString(R.string.rent_btn_label), wantedEmoji)
        binding.tvBtnRent.text = String.format(getString(R.string.wanted_btn_label), rentEmoji)

        binding.tvBtnWanted.setOnClickListener {
            val activity = activity as HomeActivity
            activity.goWantHomeActivity()
        }

        binding.tvBtnRent.setOnClickListener {
            val intent = Intent(binding.root.context, TransferActivity::class.java)
            startActivity(intent)
        }

        binding.btnFindHome.setOnClickListener {
            val intent = Intent(binding.root.context, FindHomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnWnatedHome.setOnClickListener {
            val intent = Intent(binding.root.context, WantHomeResultActivity::class.java)
            startActivity(intent)
        }

        binding.fabHomeMenu.setOnClickListener {
            toggleFab()
        }
    }

    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            binding.tvBtnWanted.visibility = View.GONE
            binding.tvBtnRent.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabHomeMenu, View.ROTATION, 45f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            binding.tvBtnWanted.visibility = View.VISIBLE
            binding.tvBtnRent.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabHomeMenu, View.ROTATION, 0f, 45f).apply { start() }
        }

        isFabOpen = !isFabOpen

    }
}
