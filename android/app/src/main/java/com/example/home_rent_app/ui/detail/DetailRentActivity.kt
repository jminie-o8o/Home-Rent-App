package com.example.home_rent_app.ui.detail

import android.graphics.PointF
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.home_rent_app.R
import com.example.home_rent_app.databinding.ActivityDetailRentBinding
import com.example.home_rent_app.ui.chatting.RentMessageListActivity
import com.example.home_rent_app.ui.detail.adapter.DetailThumbnailAdapter
import com.example.home_rent_app.ui.detail.adapter.IconAdapter
import com.example.home_rent_app.ui.detail.viewmodel.DetailHomeViewModel
import com.example.home_rent_app.util.UiState
import com.example.home_rent_app.util.logger
import com.example.home_rent_app.util.repeatOnStarted
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRentActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding: ActivityDetailRentBinding by lazy {
        ActivityDetailRentBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailHomeViewModel by viewModels()

    private lateinit var position: CameraUpdate
    lateinit var map: NaverMap

    private val mapFragment: MapFragment by lazy {
        val fm = supportFragmentManager
        fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
    }

    private var totalPicSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val window = window

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        mapFragment.getMapAsync(this)

        val id = intent?.getIntExtra("homeId", -1)

        if (id != null) {
            viewModel.getDetailHomeData(id)
        }

        val adapter = DetailThumbnailAdapter()

        val optionAdapter = IconAdapter()
        val securityAdapter = IconAdapter()

        binding.vpHomePic.adapter = adapter
        binding.rvOptionList.adapter = optionAdapter
        binding.rvSecurityList.adapter = securityAdapter

        binding.vpHomePic.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.tvPageCount.text = getString(R.string.page_count, position + 1, totalPicSize)
                }
            })

        repeatOnStarted {
            viewModel.detailHomeData.collect {
                when (it) {
                    is UiState.Success -> {
                        logger("detailHomeData : ${it.data.availableFrom}")
                        binding.item = it.data
                        adapter.submitList(it.data.houseImages)
                        optionAdapter.submitList(it.data.facilities)
                        securityAdapter.submitList(it.data.securityFacilities)
                        viewModel.getPosition()
                        totalPicSize = it.data.houseImages.size
                    }
                    is UiState.Error -> {
                        logger("detailHomeData : ${it.message}")
                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is UiState.Loading -> {
                        logger("Loading...")
                    }
                    else -> logger("Loading...")
                }
            }
        }

        binding.btnGoChat.setOnClickListener {
            repeatOnStarted {
                viewModel.joinNewChannel().collect {
                    startActivity(RentMessageListActivity.newIntent(binding.root.context, it))
                    finish()
                }
            }
        }
        goBack()
    }

    override fun onMapReady(naverMap: NaverMap) {
        map = naverMap
        map.uiSettings.apply {
            isCompassEnabled = true
            isScaleBarEnabled = false
        }
        setPositionObserve()
//        viewModel.getPosition()
    }

    private fun setPosition(latLng: LatLng) {
        val marker = Marker()
        marker.icon = OverlayImage.fromResource(R.drawable.ic_marker)
        marker.anchor = PointF(0.5f, 0.5f)
        marker.position = latLng // 마커 위치를 설정
        marker.map = map // 마커가 속해있는 지도를 우리가 사용하는 지도로 설정
        position = CameraUpdate.scrollTo(latLng) // 카메라의 좌표 설정 로직
        map.moveCamera(position) // 카메라를 이동시키는 로직
    }

    private fun setPositionObserve() {
        repeatOnStarted {
            viewModel.position.collect {
                when (it) {
                    is UiState.Success -> {
                        val latLng = LatLng(it.data.y, it.data.x)
                        setPosition(latLng)
                    }
                    is UiState.Error -> {
                        Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        logger("Loading....")
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun goBack() {
        binding.tvNavigationBack.setOnClickListener {
            finish()
        }
    }
}
