package com.example.home_rent_app.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.home_rent_app.databinding.DialogShootOrAlbumBinding

class ShootOrAlbumDialog : DialogFragment() {

    private val binding: DialogShootOrAlbumBinding by lazy {
        DialogShootOrAlbumBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setShootClick()
        setAlbumClick()
    }
    // 다이얼 로그 만들고 사진 찍거나 가져오기
    private fun setShootClick() {
        binding.cloShoot.setOnClickListener {
        }
    }

    private fun setAlbumClick() {
        binding.cloAlbum.setOnClickListener {
        }
    }
}
